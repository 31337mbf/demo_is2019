def call(ppSettings) {
	stage("Настройка конвейера") {
		log.debug("Поиск конфигурационного файла");
		if(!v8config.existCFG()) {
			log.error("Конфигурационный файл `v8config.json` не обнаружен в репозитории. Сборка прервана");
		}

		def configVersion = v8config.getParam("version");
		if(configVersion != "2.1") {
			log.error("Конфигурационный файл `v8config.json` должен быть версии `2.1`. Сборка прервана");
		}

		ppSettings.isRelease = ppSettings.pipelineType == "RELEASE";						// Сборка релиза
		ppSettings.isNightBuild = ppSettings.pipelineType == "DEVELOP";						// Ночная сборка
		ppSettings.tempDir = fileUtils.createChildFilePath(WORKSPACE, "tmp").toString(); 	// каталог временных файлов

		log.debug("Загрузка настроек конвейера");
		loadSettings(ppSettings, configVersion);
		correctSettings(ppSettings);

		log.printParams(ppSettings);
	}
}

def loadSettings(ppSettings, configVersion = "2.1") {
	ppSettings.configVersion = configVersion;																// версия конфигурационного файла
	ppSettings.onecVersion = v8config.getParam("ВерсияПлатформы", "0.0.0.0");								// версия платформы
	ppSettings.edtRepo = v8config.getParam("ФорматEDT", false);												// формат репозитория EDT

	ppSettings.autotest = v8config.getParam("АвтоТестирование/Выполнять", true);							// автотестирование используется 
	 	ppSettings.autotestEDT = v8config.getParam("АвтоТестирование/ИспользоватьRINGEDT", true);			// базовое тестирование EDT
		ppSettings.autotestSmoke = v8config.getParam("АвтоТестирование/ИспользоватьДымовыеТесты", false);	// дымое тестирование

	ppSettings.statAnalyze = v8config.getParam("АнализИсходников/Выполнять", true);							// статический анализ используется
	 	ppSettings.statAnalyzeBSLLS = v8config.getParam("АнализИсходников/ИспользоваьBSLLS", true);			// статический анализ BSLLS без SonarQube
}

def correctSettings(ppSettings) {
	ppSettings.autotestEDT = ppSettings.autotestEDT && ppSettings.edtRepo && ppSettings.autotest;
	ppSettings.autotestSmoke = ppSettings.autotestSmoke && ppSettings.autotest;
	ppSettings.autotest = ppSettings.autotest && ppSettings.autotestEDT && ppSettings.autotestSmoke;

	ppSettings.statAnalyzeBSLLS = ppSettings.statAnalyzeBSLLS && ppSettings.statAnalyze;
}
