package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
//@Table(name = "USER")  // DB table이름 설정
public class Member extends BaseEntity{

    /*
    @Id
    private Long id;
    private String name;

    //public Member() {}
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     */

    /*
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;
*/
    //@Column(name = "TEAM_ID")
    //private Long teamID;

    // jpa에 1:n n:1 관계인지 알려줘야함 => db관점에서
    @ManyToOne
                  //@ManyToOne(fetch = fetchType.EAGER) => 즉시로딩 , JOIN해서 보여줌(프록시 필요없음) => 디폴트
                  //          (fetch = fetchType.LAZY)  => 분리돼서 쿼리(지연로딩) , 프록시 개체 조회 , member클래스만 db에서 조회
    @JoinColumn(name = "TEAM_ID")  // 조인할 컬럼
    private Team team;

    //Locker 단방향     양방향하려면 Locker에 mappedBy="locker" Member member 만들면 됨
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;


/*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
*/
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {    //setTeam -> changeTeam 으로 하면 필요한부분이구나 눈에 띄게 확인할수있음
        this.team = team;
        team.getMembers().add(this);
    }


    // 임베디드 타입
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 기간
    //private LocalDateTime startDate;
    //private LocalDateTime endDate;
    @Embedded
    private Period workPeriod;
    //private Period workPeriod = null;  => startDate,endDate가 null로 들어감

    // 주소
    //private String city;
    //private String street;
    //private String zipcode;
    @Embedded
    private Address HomeAddress;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="city",
                        column = @Column(name="WORK_CITY")),
                         @AttributeOverride(name="street",
                         column = @Column(name="WORK_STREET")),
                        @AttributeOverride(name="zipcode",
                        column = @Column(name="WORD_ZIPCODE"))})
    private Address workAddress;

    public Address getHomeAddress() {
        return HomeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        HomeAddress = homeAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }
}
