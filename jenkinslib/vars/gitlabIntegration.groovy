//----------------------------------------------------------------------------//
// Методы интеграции с GitLab EE
//----------------------------------------------------------------------------//

// Корректное получение исходников
def correctCheckout(projectType) {
	if(projectType == "MR") {
		checkout ([
			$class: 'GitSCM',
			branches: [[name: "${env.gitlabSourceNamespace}/${env.gitlabSourceBranch}"]],
			extensions: [
				[$class: 'PruneStaleBranch'],
				[$class: 'CleanCheckout'],
				[
					$class: 'PreBuildMerge',
					options: [
						fastForwardMode: 'NO_FF',
						mergeRemote: env.gitlabTargetNamespace,
						mergeTarget: env.gitlabTargetBranch
					]
				]
			],
			userRemoteConfigs: [
				[
					name: env.gitlabTargetNamespace,
					url: env.gitlabTargetRepoSshURL
				],
				[
					name: env.gitlabSourceNamespace,
					url: env.gitlabSourceRepoSshURL
				]
			]
		])
	} else {
		checkout scm
	}	
}
