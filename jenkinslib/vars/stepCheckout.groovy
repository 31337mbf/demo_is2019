def call(ppSettings) {
	stage("Скачивание исходников") {
		
		log.info("Очистка рабочего каталога");
		deleteDir();
		
		log.info("Скачивание исходников");
		gitlabIntegration.correctCheckout(ppSettings.projectType);
	}
}
