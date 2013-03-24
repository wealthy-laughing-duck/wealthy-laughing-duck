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

struct TUser {
   1: required string username,
   2: required string fullname,
}

struct TDate {
   1: required int year,
   2: required int month,
   3: required int day,
}

struct TPeriod {
   1: required TDate from,
   2: required TDate to,
}

service FinanceService {
  list<TOutcome> getUserOutcomes(1:required int user_id),
  list<TIncome> getUserIncomes(1:required int user_id),
  list<TUser> getAllUsers()
}

