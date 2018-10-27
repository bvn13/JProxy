# JProxy

Project aimed to create multiply proxy servers and to manage them via web UI.

## Libraries and Frameworks

1. Java 8 (only tested)
2. Maven
3. SpringBoot 2.0.6
4. H2 database (embedded)
5. JMapper (from google)

## How to Install and Run

### Installation

#### 1. Clone from repo or download a zip

```
$ wget https://github.com/bvn13/JProxy/archive/master.zip
```

#### 2. Unzip it

```
$ unzip master.zip
```

#### 3. Run maven

```
$ cd JProxy-master

$ mvn package
```

__DONE__

### Running

```
$ java -jar target/jproxy-1.0-jar
```

now open http://localhost:8080/

__DEFAULTS:__

- USER: user
- PASSWORD: pass123
- change it in `/src/main/resources/application.properties`

__screenshots__


![very simple web UI](https://raw.githubusercontent.com/bvn13/JProxy/master/img/login-page.png)

![very simple web UI](https://raw.githubusercontent.com/bvn13/JProxy/master/img/first-view.png)

![very simple web UI](https://raw.githubusercontent.com/bvn13/JProxy/master/img/proxy-form.png)