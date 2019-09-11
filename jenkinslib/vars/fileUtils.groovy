//----------------------------------------------------------------------------//
// Методы работы с файлами и файловой системой
//----------------------------------------------------------------------------//

import hudson.FilePath

//----------------------------------------------------------------------------//

// Формирует путь к файлу относительно ноды
def createFilePath(path) {
	if(env.NODE_NAME == null) {
		error "Переменная `NODE_NAME` не установлена. Возможно отсутствует секция `node`";
	} else if(env.NODE_NAME.equals("master")) { // на мастере
		return new FilePath(new File("$path"))
	} else { // на ноде
		return new FilePath(Jenkins.getInstance().getComputer(env.NODE_NAME).getChannel(), "$path");
	}
}

// Формирует путь к дочернему файлу относительно переданному с учетом ноды
def createChildFilePath(path, child, mkdir = false) {
	def original = createFilePath("$path");
	def newPath = createFilePath("${original}${File.separator}${child}");
	if(mkdir && !newPath.exists()) {
		createDirs(newPath);
	}
	return newPath;
}

// Создает каталог по указанном пути
def createDirs(path) {
	def original = createFilePath("$path");
	original.mkdirs();
}

// Проверка существования файла
def fileExist(path) {
	return createFilePath(path).exists();
}
