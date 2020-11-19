

# Inflearn-Spring_Framework_Introdution

[인프런] 스프링 프레임워크 입문

예제코드 : https://github.com/spring-projects/spring-petclinic.git



## Inversion of Control 

#### 제어의 역전 : 일반적으로 의존성에 대한 제어권을 뒤바꾸겠다. (의존성 이외에도 역전 가능!)

일반적인 예) 

- OwnerRepository 클래스의 owners객체는 내가 직접 인스턴스화 한다. (개발자가 new 연산자 지정)
- **객체를 인스턴스화 하는 것이 나(개발자)에게 의존한다.**

```java
class OwnerController {
	private OwnerRepository owners = new OwnerRepository();
}
```

제어가 역전된 예) 

- 내가 객체를 인스턴스화 하지 않아도 코드를 실행할 누군가가 대신 해줄 것이다.
- 코드상에서 repo를 초기화 하지 않음, controller 객체의 생성자에서 repo를 초기화 해 줄 수 있음
- **객체가 인스턴스화 되는 것이 나(개발자)에게 의존되지 않는다. 나(개발자) 이외의 타인이 코드 실행에 따라 인스턴스된다.**

```java
class OwnerController {
	private OwnerRepository repo;
    
    public OwnerController(OwnerRepository repo) {
        this.repo = repo;
    }
    
    // use repo ...
}

class OwnerControllerTest {
    @Test
    public void create() {
        OwnerRepository repo = new OwnerRepository();
        OwnerController controller = new OwnerController(repo);
    }
}
```



### IoC 컨테이너

#### ApplicationContext (Bean Factory)이라고 한다.

- 컨테이너 안에서 선언된 객체를 Bean이라고 한다.
- 컨테이너는 Bean들의 의존성을 관리한다.



### Bean : 스프링 Ioc 컨테이너가 관리하는 객체

#### 등록하는 방법 

- Component Scanning
  - @Component
    - @Repository
    - @Service
    - Controller
- XML 또는 자바 설정 파일에 등록(@Configuration)

#### 컨테이너에서 꺼내쓰는 법

- @Autowried 또는 @Inject
- 또는 ApplicationContext에서 getBean()



### 의존성 주입 (Dependency Injection)

