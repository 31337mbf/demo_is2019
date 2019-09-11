def call(ppSettings) {
	stage("Проверка и настройка окружения стенда") {
		log.info("Проверка версий ПО стенда");
		checkVerTool("1.0.21.1", "oscript -v", "([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+)", "OScript");
		
		if(ppSettings.edtRepo) {
			checkVerTool("edt@1.10.1:x86_64", "ring", "(edt@[0-9]+\\.[0-9]+\\.[0-9]+:x86_64)", "ring edt");
		}

		if(ppSettings.autotestEDT) {
			checkVerTool("vanessa-runner v1.7.0", "vrunner", "(vanessa-runner v[0-9]+\\.[0-9]+\\.[0-9]+)", "vanessa-runner");
		}

		if(ppSettings.statAnalyzeSonarBSLLS) {
			checkVerTool("0.10.2", "java -jar ${BSLLS_PATH} -v", "([0-9]+\\.[0-9]+\\.[0-9]+)", "BSL LS");
		}
	}
}

def checkVerTool(correctVersion, command, pattern, description) {
	log.info("Проверка наличия и версии `$description`");
	def toolVer = cmd.run(command, "Проверка версии $description", true);
	def findVer = toolVer =~ pattern;

	if(findVer.find()) {
		if(findVer[0][1] != correctVersion) {
			log.warring("Установленная версия `${findVer[0][1]}` не соответствует ожидаемой `$correctVersion`");
		}
	} else {
		catchError(stageResult: 'FAILURE') {
			log.error("$description не обнаружен");
		}
	}
}
