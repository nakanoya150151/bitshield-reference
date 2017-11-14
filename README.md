# bitshield-reference
SpringBootのリファレンス実装を作成


## プロジェクト(モジュール)構成
bitshield-coreを各モジュールの依存関係に追加する想定。
TODO

## パッケージポリシー
TODO

## レイヤリング
 
 - プレゼン層
  + RestAPIのエンドポイントを提供する。
  + 主にControllerクラスで構成される。
  + Controllerクラスではビジネスロジックの呼び出しにServiceクラスを利用する。
 
 - アプリケーション層
  + 業務ロジックの呼び出し口となる。
  + 主にServiceクラス、SharedServiceクラスで構成される。
  + 原則として、Serviceクラスのメソッドがトランザクション境界となる。
  + Seriveから呼び出されることが想定されるメソッドはSharedServiceクラスに処理を委譲する。
 
 - ドメイン層
  + 業務の概念を表現したオブジェクト。
  + 主に@Entityを付与したクラスで構成される。
  + EntityBaseを継承し、原則テーブルと1対1で対応する。
  
 - インフラストラクチャ層
  + ドメインオブジェクトを管理する。
  + 主にRepositoryクラスで構成される。
  
### Controllerクラス

### Serviceクラス

### Entityクラス

### Repositoryクラス

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
