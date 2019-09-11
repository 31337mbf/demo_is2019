//----------------------------------------------------------------------------//
// Методы работы с конфигурационным файлом v8config.json
//----------------------------------------------------------------------------//

// Возвращает признак наличия конфигурационного файла
def existCFG() {
	return fileUtils.fileExist(pathv8config());
}

// Читает параметр из конфигурационного файла
def getParam(name, defValue = "") {

	def inputFile = readFile "${pathv8config()}";
	def config = new groovy.json.JsonSlurper().parseText(inputFile);

	def value = defValue;
	def paramAr = name.split("/");
	def configFild = config["GLOBAL"];
	def fildName = name;
	if(paramAr.length == 2) {
		configFild = config[paramAr[0]];
		fildName = paramAr[1];
	}

	if(configFild != null) {
		value = configFild[fildName] != null ? configFild[fildName] : defValue;
	}

	config = null;
	return value;
}

def pathv8config() {
	def filePath = fileUtils.createChildFilePath(WORKSPACE, "v8config.json");
	return "$filePath"
}
