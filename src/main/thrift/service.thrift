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

struct TCategory {
    1: required int id,
    2: required string name,
    3: optional int parent_id
}

typedef list<TCategory> TCategoryPlainTree

struct TDate {
    1: required int year,
    2: required int month,
    3: required int day,
}

struct TPeriod {
    1: required TDate from,
    2: required TDate to,
}

struct TCriteria {
    1: required TPeriod period,
    2: required list<TUser> users
}

enum CategoryType {
    INCOME = 1,
    OUTCOME = 2
}

service FinanceService {

  /**
   * Returns all outcomes for a given user
   */
  list<TOutcome> getUserOutcomes(
    1:required int user_id
  ),

  /**
   * Returns all incomes for a given user
   */
  list<TIncome> getUserIncomes(
    1:required int user_id
  ),

  /**
   * Returns list of all users
   */
  list<TUser> getAllUsers(),

  void createCategoryTreeNode(
    1:required int parent_id,
    2:required string name,
    3:required CategoryType type
  ),

  void moveCategoryTreeNode(
    1:required int id,
    2:required int new_parent_id
  ),

  void renameCategoryTreeNode(
    1:required int id, 2:required string new_name
  ),

  void removeCategoryTreeNode(
    1:required int id
  ),

  /**
   * Returns category tree
   */
  TCategoryPlainTree getCategoryTree(
    1:required CategoryType type
  )
}
