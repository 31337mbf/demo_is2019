def call(ppSettings) {
	log.info("Определение типа проекта и варианта конвейера");
	if(env.gitlabSourceBranch == null && env.BRANCH_NAME == null) {
		catchError(stageResult: 'FAILURE') {
			log.error("Проект не связан с GitLab, работать с ним не получится!");
		}
	} 

	log.debug("env.gitlabSourceBranch = `$env.gitlabSourceBranch`");
	log.debug("env.gitlabTargetBranch = `$env.gitlabTargetBranch`");
	log.debug("env.BRANCH_NAME = `$env.BRANCH_NAME`");
	if(env.gitlabSourceBranch != null && env.gitlabTargetBranch != null) {
		ppSettings.projectType = "MR";
		ppSettings.pipelineType = "MR";
	} else {
		ppSettings.projectType = "BRANCH";
		if(env.BRANCH_NAME.startsWith('feature/')) {
			ppSettings.pipelineType = "FEATURE";
		} else if(env.BRANCH_NAME == 'develop') {
			ppSettings.pipelineType = "DEVELOP";
		} else if(env.BRANCH_NAME == 'master') {
			ppSettings.pipelineType = "MASTER";
		} else if(env.BRANCH_NAME.startsWith('release/')) {
			ppSettings.pipelineType = "RELEASE";
		}
	}
	
	if(ppSettings.pipelineType == "UNKNOWN") {
		log.error("Неудалось определить вариант конвейера.");
	}
}
