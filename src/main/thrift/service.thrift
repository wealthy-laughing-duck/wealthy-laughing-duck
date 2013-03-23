namespace java com.blogspot.symfonyworld.wealthylaughingduck.thrift.generated
namespace php SymfonyWorld.WealthyLaughingDuck

typedef i32 int

struct TOutcome {
   1: required double amount,
   2: required string user,
   3: required string category,
   4: optional string comment,
}

struct TIncome {
   1: required double amount,
   2: required string user,
   3: required string category,
   4: optional string comment,
}

service FinanceService {
  list<TOutcome> getUserOutcomes(1:required int user_id),
  list<TIncome> getUserIncomes(1:required int user_id)
}

