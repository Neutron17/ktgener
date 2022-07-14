# Boilerplate generator (kotlin version)

## Installation
```shell
git clone https://github.com/Neutron17/ktgener.git --recursive
cd ktgener
```
### Linux
```shell
chmod +x build.sh
./build.sh
```
### Windows
```shell
.\build.bat
```

## Usage
- ```-t/--type:```

    boilerplate type: for example 'c' generates a c boilerplate project

    boilerplate templates are stored in the ```templates/``` directory

General syntax:
```shell
java -jar ktgener.jar -t <project type> -o <output> -u <username>
```
Example:
```
java -jar ktgener.jar -t maven -o foo -u neutron17
```

## Contribution
All contribution is welcome

There is a ```TODO.md``` file, containing the todos
## License
this project is licensed under the ```GPL 3.0 License```, use it as such