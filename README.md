

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
- XML 또는 자바 설정 파일에 등록(@Configuratio 되어 있는 Class에서  @Bean)

#### 컨테이너에서 꺼내쓰는 법

- @Autowried 또는 @Inject
- 또는 ApplicationContext에서 getBean()



### 의존성 주입 (Dependency Injection)

- 어노테이션이 생략된 경우가 존재(Spring Lifecycle 중에 개입하는 경우 또는 단일 생성자의 매개변수)
- 생성자, 필드, Setter에서 @Autowired 또는 @Inject 사용



### AOP : Aspect Oriented Programming

- 흩어진 한 곳으로 뭉치기
- 다른 부분에서 **반복해서 쓰는 코드들을 한 곳으로 모아 모듈화** ➡ **흩어진 관심사(Crosscutting Concerns)**
- 흩어진 관심사를 **Aspect로 모듈화**하여 비즈니스 로직에서 분리하여 **재사용**



@LogExecutionTime 이라는 어노테이션 정의

```java
@Target(ElementType.METHOD) // 적용 범위
@Retention(RetentionPolicy.RUNTIME) // 유지
public @interface LogExecutionTime {

}
```

@LogExecuteTime이 선언된 페이지가 열릴 때 마다 실행시간을 측정하는 로직 정의(재사용되는 로직)

```java
@Component
@Aspect
public class LogAspect {

	Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object ret = joinPoint.proceed();

		stopWatch.stop();
		logger.info(stopWatch.prettyPrint());

		return ret;
	}

}
```

**@Around** 외에 타겟 메서드의 **Aspect** 실행 시점을 지정할 수 있는 어노테이션이 있다. 
(Around는 모든 시점에서 모든 실행 시점에 적용)

- @Before (이전) : 어드바이스 타겟 메소드가 호출되기 전에 어드바이스 기능을 수행
- @After (이후) : 타겟 메소드의 결과에 관계없이(즉 성공, 예외 관계없이) 타겟 메소드가 완료 되면 어드바이스 기능을 수행
- @AfterReturning (정상적 반환 이후)타겟 메소드가 성공적으로 결과값을 반환 후에 어드바이스 기능을 수행
- @AfterThrowing (예외 발생 이후) : 타겟 메소드가 수행 중 예외를 던지게 되면 어드바이스 기능을 수행
- @Around (메소드 실행 전후) : 어드바이스가 타겟 메소드를 감싸서 타겟 메소드 호출전과 후에 어드바이스 기능을 수행



### PSA : Portable Service Abstraction

잘 만든 인터페이스