# bitshield-reference
SpringBootのリファレンス実装を作成


## プロジェクト(モジュール)構成
bitshield-coreを各モジュールの依存関係に追加する想定。
TODO

## パッケージポリシー
TODO

## レイヤリング
 
 * プレゼン層
   * RestAPIのエンドポイントを提供する。
   * 主にControllerクラスで構成される。
   * Controllerクラスではビジネスロジックの呼び出しにServiceクラスを利用する。
 
 * アプリケーション層
   * 業務ロジックの呼び出し口となる。
   * 主にServiceクラス、SharedServiceクラスで構成される。
   * 原則として、Serviceクラスのメソッドがトランザクション境界となる。
   * Seriveから呼び出されることが想定されるメソッドはSharedServiceクラスに処理を委譲する。
 
 * ドメイン層
   * 業務の概念を表現したオブジェクト。
   * 主に@Entityを付与したクラスで構成される。
   * EntityBaseを継承し、原則テーブルと1対1で対応する。
  
 * インフラストラクチャ層
   * ドメインオブジェクトを管理する。
   * 主にRepositoryクラスで構成される。
  
### Controllerクラス
```
@RestController
@RequestMapping("/addresses")
@AllArgsConstructor
public class AddressController {

	private final AddressService addressService;

	@GetMapping("/{id}")
	public ResponseEntity<AddressResponse> get(@PathVariable("id") Long id) {
		Address address = this.addressService.getAddress(id);
		AuditLogger.log(CustomerAuditLogCode.ADDRESS_READ.getCode(true));
		return new ResponseEntity<>(AddressResponse.newAddressResponse(address), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<AddressResponse>> list() {
		List<Address> addresses = this.addressService.listAddress();
		AuditLogger.log(CustomerAuditLogCode.ADDRESS_READ.getCode(true));
		return new ResponseEntity<>(AddressResponse.newAddressesResponse(addresses), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<AddressResponse> create(@RequestBody AddressPostRequest req) {
		Address address = AddressPostRequest.newAddress(req);
		Address created = this.addressService.create(address);
		AuditLogger.log(CustomerAuditLogCode.ADDRESS_CREATE.getCode(true), created, req);
		return new ResponseEntity<>(AddressResponse.newAddressResponse(created), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AddressResponse> update(@PathVariable("id") Long id, @RequestBody AddressPutRequest req) {
		Address address = AddressPutRequest.newAddress(req);
		Address updated = this.addressService.update(id, address);
		AuditLogger.log(CustomerAuditLogCode.ADDRESS_UPDATE.getCode(true), updated, req);
		return new ResponseEntity<>(AddressResponse.newAddressResponse(updated), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		return  this.addressService.findAddress(id).map(address -> {
			this.addressService.delete(address);
			AuditLogger.log(CustomerAuditLogCode.ADDRESS_DELETE.getCode(true), address, id);
			return new ResponseEntity<>(HttpStatus.OK);
		}).orElseGet(() -> {
			AuditLogger.log(CustomerAuditLogCode.ADDRESS_DELETE.getCode(false), id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		});
	}
}

```

### Serviceクラス
```
@Service
@AllArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;

	public Address getAddress(Long id) {
		return this.addressRepository.findById(id).orElseThrow(() -> new ApplicationException(ApplicationHttpErrors.UNEXPECTED));
	}

	public Optional<Address> findAddress(Long id) {
		return this.addressRepository.findById(id);
	}

	public List<Address> listAddress() {
		return this.addressRepository.findAll();
	}

	public Address create(Address address) {
		return this.addressRepository.save(address);
	}

	public Address update(Long id, Address address) {
		Address old = this.getAddress(id);
		return old.update(address);
	}
        ・・・
}
```

### Entityクラス
```
@Entity
@Data
@Where(clause = "is_deleted = 0")
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Address extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long walletId;

	private String path;

	public Address update(Address address) {
		this.walletId = address.getWalletId();
		this.path = address.getPath();
		return this;
	}
}
```

### Repositoryクラス
```
public interface AddressRepository extends JpaRepositoryBase<Address, Long>, JpaSpecificationExecutor<Address> {

}

@NoRepositoryBean
public interface JpaRepositoryBase<T extends EntityBase, ID extends Serializable> extends JpaRepository<T, ID> {

	default void logicalDelete(T entity) {
		entity.setDeleted(true);
	}
}

```


### 例外ハンドリング

## 実装ポリシー
Java8/9とJava6/7は全く別物と考えて実装を行う必要がある。
モダンな文法とLombokを積極的に駆使することを推奨する。

 - ラムダ式、メソッド参照の活用
```
   [Java7以前]
   List<String> list = new ArrayList<>();
   for (String str : strList) {
      if (str.length > 4) {
          String upper = str.toUpperCase();
          str.add(upper);
      }
   }
   return list;
  
   [Java8以降]　
   return strList.stream()
          .filter(str -> str.length > 4)
          .map(String::toUpperCase)
          .collect(Collectors.toList());
```
 - Optionalの利用
```
   [Java7以前]
   Cashflow cashflow = this.findById(id);
   if (cashflow != null) {
      this.cashflowRepository.save(cashflow);
   }
   
   [Java8以降]　
   Optinal<Cashflow> cashflow = this.repository.findById(id);
   cashflow.ifPresent(data -> this.cashflowRepository.save(data);
```

- LocalDateTimeの利用
```
```

- Lombokの利用
```
   [Lombokなし]
   public class Data {
   	
	private String str1;
	private String str2;
	
	public Data(String str1, String str2) {
		this.str1 = str1;
		this.str2 = str2;
	}
	
	public String getStr1() {
		return this.str1;
	}
	
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	
	・・・
   }
   
   [Lombokあり]
   @Data
   @AllArgsConstructor
   public class Data {
   	
   	private String str1;
	private String str2;
   }

```
## テストポリシー
JUnit5を使用する。
Controller、Service、Entity毎にテストクラスを作成し、
他レイヤーの呼び出しはMockitoを使用する。
テスト実行を高速化するため、DIコンテナを使用せずにテストが完結するよう
ControllerとServiceではコンストラクタインジェクションを使用する。
```
private MockMvc mockMvc;
@Mock
private AddressService mockAddressService;

@BeforeAll
public void setUp() throwsException {
	mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(mockAddressService))
		.addFilter(new ApiOncePerRequestFilter)
		.build();
}

@Test
public void testGet() throws Exception {
	Mockito.when(mockAddressService.getAddress(Mockito.anyLong()))
		.thenReturn(new Address(100L, 9999L, "999path"));
		
	mockMvc.perform(get("/addresses/100"))
		.andExpect(
			status().isOk()
		).andExpect(
			result -> assertThat(result.getResponse().getContentAsString(), is("{\"id\": 100, (省略)}")
		).andDo(
			print()
		);
}
```
