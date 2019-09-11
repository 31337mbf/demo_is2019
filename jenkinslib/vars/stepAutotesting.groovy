def call(ppSettings) {
	stage("Автотестирование") {
		log.info("Автотестирование");
		if(!ppSettings.autotest) {
			log.info("Автотестирование не требуется");
			return;
		}

		if(ppSettings.autotestEDT) {
			vrunner.edtValidate(ppSettings);
		}

		if(ppSettings.autotestSmoke && ppSettings.isRelease) {
			log.info("Дымовые тесты при сборке релиза...");
			log.info("Не реализовано");
		}
	}
}
