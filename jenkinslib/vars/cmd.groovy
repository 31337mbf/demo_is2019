//----------------------------------------------------------------------------//
// Методы выполнения команд ОС
//----------------------------------------------------------------------------//

// Запускает команду ОС на выполнение
def run(command, label = "", returnStdout = false, encoding = "UTF-8") {
	log.debug("Исполняемая команда: `$command`")
	def result = "";
	if(isUnix()) {
		result = sh script: "$command", label: "$label", encoding: "$encoding", returnStdout: returnStdout;
	} else {
		result = bat script: "chcp 65001> nul \n $command", label: "$label", encoding: "$encoding", returnStdout: returnStdout;
	}
	if(returnStdout) {
		log.debug("Результат выполнения: `${result.toString()}`");
		return result.toString();
	}
}
