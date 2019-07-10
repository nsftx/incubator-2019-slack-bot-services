@Entity
@Table(name = "User")
public classUser {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String imageUrl;
    @Column
    private UserType userType;
