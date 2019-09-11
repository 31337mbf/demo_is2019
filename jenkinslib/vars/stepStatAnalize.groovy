def call(ppSettings) {
	stage("Статический анализ кода") {
		log.info("Статический анализ кода");
		if(!ppSettings.statAnalyze) {
			log.info("Статический анализ кода не требуется");
			return;
		}

		if(ppSettings.statAnalyzeBSLLS) {
			log.info("Статический анализ кода средствами BSLLS");
			def srcPath = fileUtils.createChildFilePath(WORKSPACE, "configuration");
			cmd.run("java -jar \"${BSLLS_PATH}\" -a -s \"${srcPath}\" -o \"${WORKSPACE}\" -r junit");			
		}
	}
}
