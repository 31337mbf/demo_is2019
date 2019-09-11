//----------------------------------------------------------------------------//
// Методы, используемые для отладки
//----------------------------------------------------------------------------//

// Выводит информационное сообщение в режиме debug = yes
def debug(message) {
	printLog(message, "DEBUG");
}

// Выводит информационное сообщение
def info(message) {
	printLog(message, "INFO");
}

// Выводит ошибку
def error(message) {
	printLog(message, "ERROR");
	currentBuild.result = "FAILURE";
	throw new Exception(message);
}

// Выводит предупрждение
def warring(message) {
	printLog(message, "WARRING");
}

// Признак режима debug
def isDebug() {
	return env.DEBUGMODE == 'on';
}

// печатает параметры конвейера
def printParams(ppSettings) {
	if(!isDebug()) {
		return;
	}
	debug("Настройки конвейера");
	debug(ppSettings);
}

//----------------------------------------------------------------------------//

def printLog(message, level) {

	if(level == "DEBUG" && !isDebug()) {
		return;
	}
	def now = (new Date()).format("dd.MM.yy HH:mm:ss", TimeZone.getTimeZone('Europe/Moscow'));
	println "$now MSK: [$level]: ${message.toString()}"
}
