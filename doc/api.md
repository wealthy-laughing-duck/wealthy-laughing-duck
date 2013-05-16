RESTful API
===========

 * json.php?action=users

    [
        {username:pmc, fullname:Paul McCartney},
        {username:jl, fullname:John Lennon},
        {username:gh, fullname:George Harrison},
        {username:rs, fullname:Ringo Starr}
    ]

 * json.php?action=incomeCategories

    [
        {id:35, name:amrs trade, parent_id:null},
        {id:36, name:trips, parent_id:35},
        ...
        {id:63, name:category-name, parent_id:null}
    ]

 * json.php?action=outcomeCategories

    [
        {id:1, name:food, parent_id:null},
        {id:2, name:bills, parent_id:null},
        ...
        {id:55, name:souvenirs, parent_id:20}
    ]

