# Desktop Application for Organizing Seminars in Java
The objective of this project was to develop a desktop application in Java for organizing seminars that is socket-based, with its own MySQL database. This was the final project for the mandatory course Software Design and the elective course Advanced Programming at the Faculty of Organizational Sciences, Information Systems and Technologies.

## Project Overview
The project follows a simplified Larman's methodology¹ of software development, consisting of these steps:
* Gathering user requirements;
* Analysis;
* Design;
* Implementation;
* Testing.

Below are all use cases of the software, shown using a UML use case diagram:
<img src="https://iili.io/2tRdPJS.jpg" width=400 height=300>

The software systems contains tests written in JUnit for all system operations and domain classes. Every system operation and domain class is documented.

## Software Structure
It consists of three separate subprojects:
* Client - client side GUI application;
* Server - server side GUI application;
* Common - subproject containing files required for both client and server sides.

Below is a diagram of the entire software system, focusing on the structure of the application logic.
<img src="https://iili.io/2tR323l.jpg" width=1300 height=1300>

## Literature
¹ Влајић, Синиша. *Пројектовање софтвера (скрипта - радни материјал), вер. 1.3*. Факултет организационих наука. Београд 2020. Available at: [ResearchGate](https://www.researchgate.net/publication/344459754_PROJEKTOVANE_SOFTVERA_SKRIPTA_-_RADNI_MATERIJAL_-_2020).

## Acknowledgements
I would like to thank my mentor [Dragica Ljubisavljević](https://rs.linkedin.com/in/dragica-ljubisavljevic) for her guidance throughout the development of the Software Design component of the project.