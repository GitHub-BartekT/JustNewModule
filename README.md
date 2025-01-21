# Just New Module
The **Just New Module** application creates structures for new modules.
It significantly reduces the time needed to create a new functionality structure.
This allows you to focus on coding business logic, not on repetitive tasks.

## How it works
### Problem
IT projects are organized to ensure easy navigation.
When creating a new feature, you often need to repeatedly create the same files from scratch.
For example, in a hexagonal architecture, you might create: 
* ModulePort
* ModuleFacade
* ModuleMapper
* ModuleRepository
* Module (Entity)
* ModuleDto, or
  * ModuleReadModel
  * ModuleWriteModel
* ModuleNotFoundException
* ModuleController

There are numerous repetitive files to create before you can even start coding the business logic.

### Solution
The **Just New Module** app generates all these files with the required annotations automatically.
* If you use Spring Boot, it adds ```@Service``` and other annotations.
* If you use Lombok, it adds Lombok's annotations.

Once you adjust the project structure to your needs, the app can create REST-based projects automatically.

### Website
🌐[Go to the project website!](https://justnewmodule.com/)🌐

## Pending features

### Directory
  * Reading configuration from ```pom.xml``` 
  * Creating directory structure
  * Visualize directory structure
  * Save user settings to XML file
### Files
  * Create foundational files
  * Create files with required annotations
  * Adds entries for ```@ControllerAdvice```
### Architecture
* Support for:
  * Hexagonal architecture
  * Spaghetti code
  * Microservices

## Contact:
https://www.linkedin.com/in/bartlomiejtucholski/
https://iseebugs.pl/

## License
This project is licensed under the GNU Lesser General Public License v2.1 (LGPL v2.1).
See the [LICENSE.txt](LICENSE.txt) file for details.

Copyright (c) 2025 Bartłomiej Tucholski, ISeeBugs.pl

