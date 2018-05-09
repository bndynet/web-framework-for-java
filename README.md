	
	 _______                   __                __    __              __     
	/       \                 /  |              /  \  /  |            /  |  
	$$$$$$$  | _______    ____$$ | __    __     $$  \ $$ |  ______   _$$ |_   
	$$ |__$$ |/       \  /    $$ |/  |  /  |    $$$  \$$ | /      \ / $$   |
	$$    $$< $$$$$$$  |/$$$$$$$ |$$ |  $$ |    $$$$  $$ |/$$$$$$  |$$$$$$/   
	$$$$$$$  |$$ |  $$ |$$ |  $$ |$$ |  $$ |    $$ $$ $$ |$$    $$ |  $$ | __ 
	$$ |__$$ |$$ |  $$ |$$ \__$$ |$$ \__$$ | __ $$ |$$$$ |$$$$$$$$/   $$ |/  | 
	$$    $$/ $$ |  $$ |$$    $$ |$$    $$ |/  |$$ | $$$ |$$       |  $$  $$/ 
	$$$$$$$/  $$/   $$/  $$$$$$$/  $$$$$$$ |$$/ $$/   $$/  $$$$$$$/    $$$$/  
	                              /  \__$$ |                                  
	                              $$    $$/                                   
	                               $$$$$$/                                    

# Web Framework - A Starter Project for Spring Boot

**NOTE** : Use **MySQL v5.7+**, if JDBC exception occurred, please drop all tables and let it recreate automatically.

**Home Page**
![](https://raw.githubusercontent.com/bndynet/web-framework-for-java/master/docs/img/home.png)

**Admin Page**
![](https://raw.githubusercontent.com/bndynet/web-framework-for-java/master/docs/img/admin-home.png)


## Getting Started

1. Change data source(MYSQL) url in application.yml and create your database without tables (will be generated automatically)
1. Install dependencies
1. `mvn spring-boot:run` to run application
1. Browse http://localhost:9090

## Build your website

This application has integrated simple content management subsystem. 
By following steps, you can easily build your website.

1. Clone repo
1. Run `mvn package` to generate war package
1. Deploy war to your tomcat
1. Set your database(MySQL) in application.yml
1. Open your site
1. Create your first Admin account
1. Add your Channels
1. Add Menus for Channels

    Note that link parameters: `{id: channelId}`

1. Change your Channel content
1. Congratulations! Go to home page, you will see your website.

## Features

- User Management
- Role Management
- Menu Management
- Application Management
- File Resource Management
- User Profile
- OAuth Endpoints
- International Support
- Skin Support
- Content Management System
- Full Text Search

## Development

### Locations

- **src/main/java/net/bndy/wf/modules**: Java Modules (services and RESTful API)
- **src/main/java/net/bndy/wf/controller**: Controllers
- **src/main/resources/templates**: Thymeleaf Modules
- **src/main/resources/public/docs/api**: Auto-generated API Documentation (Swagger UI)
- **src/main/resources/static/apps/admin**: AngularJS Application for Admin Panel
    - **src/main/resources/static/apps/admin/modules**: AngularJS Modules
    - **src/main/resources/static/apps/admin/modules/example**: Style Examples 
    
### Available Frontend Components

ui-actions, ui-dialog, ui-html-editor, ui-input, ui-menu-tree, ui-no-data, ui-notifications, ui-page-content, ui-page-header, ui-search, ui-tree, ui-upload, ui-wait-on
    
### Recommendations

- Non-Frontend

    You just use thymeleaf and not angularjs, you can define controllers in **src/main/java/net/bndy/wf/controller** and thymeleaf templates in **src/main/resources/templates**.

- AngularJS

    You can implement api in **src/main/java/net/bndy/wf/modules** and angularjs controllers in **src/main/resources/static/apps/admin/modules**. Each feature should include one html file and one js file.

    **Call API (`appService` service)**

    `[ajaxGet|ajaxSave|ajaxDelete]('/api/...'[, data])` to request resources, `ajaxSave` instead of `ajaxPut` and `ajaxPost` which will call `ajaxPut` or `ajaxPost` according to **data.id**. 
    
    **Dialog & Notification (`appDialog` service)**

    `[loading|wait|clearWait|success|info|warning|error|alert|confirm|confirmDeletion|show|showWin]`
    
- International (Languages)

    You can define both of backend languages and frontend languages in **src/main/resources/i18n/**. And you can use it in AngularJS modules directly. 

    **Examples**:
    
    `welcome.message=Hi, {0}!` in i18n/messages.properties

    ```html
    <!-- call in AngularJS -->
    <span ng-bind="{welcome.message|translate:[name]}"></span>

    <!-- call in Thymelefa -->
    <span th:text="#{welcome.message(${name})}"></span>
    ```


## OAuth Endpoints

- **GET** /oauth/authorize?response_type=code&scope&client_id&redirect_uri

  Redirect to `redirect_uri?code=...`

- **POST** /oauth/token?grant_type=authorization_code&client_id&client_secret&code&redirect_uri

    ```json
    {
        "access_token": "",
        "token_type": "bearer",
        "refresh_token": "",
        "expires_in": 10000,
        "scope": "",
        "avatar": "",
        "username": "yourname"
    }
    ```
