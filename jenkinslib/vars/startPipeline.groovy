// Стартует pipeline для проекта
def call(body) {
	
	properties([disableConcurrentBuilds()]);

	def ppSettings = [:];
	ppSettings.projectType = "UNKNOWN";			// тип проекта
	ppSettings.pipelineType = "UNKNOWN";		// тип конвейера

	stage("Подготовка к началу работ") {
		catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
			setProjectType(ppSettings);
		}
	}

	catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
		stepCheckout(ppSettings);
		stepConfigurePipeline(ppSettings);
		stepCheckAndPrepareStandEnv(ppSettings);
	}

	catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
		def tasks = [:];
		
		tasks."Автотестирование" = {
			stepAutotesting(ppSettings);
		}

		tasks."Статический анализ" = {
			stepStatAnalize(ppSettings);
		}

		log.info("Запуск сценариев тестирования");
		parallel tasks;

	}

	stage("Завершение работы") {
		log.info("Обработка и сохранение отчетов");
		junit keepLongStdio: true, testResults: '**/*junit.xml';
	}
}
