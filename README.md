	
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

# Web Framework - A Maven Project

![](https://raw.githubusercontent.com/bndynet/web-framework-for-java/master/docs/img/home.png)

![](https://raw.githubusercontent.com/bndynet/web-framework-for-java/master/docs/img/admin-dashboard.png)
	
## Getting Started

1. Change data source(MYSQL) url in application.yml
1. Install dependencies
1. Run Application
1. Browse http://localhost:9090

## OAuth Endpoints

1. /oauth/authorize | GET | response_type=code&scope&client_id&redirect_uri

	Redirect to `redirect_uri?code=...`

1. /oauth/token | POST | grant_type=authorization_code&client_id&client_secret&code&redirect_uri

	```json
	{
	"access_token": "",
	"token_type": "bearer",
	"refresh_token": "",
	"expires_in": 8363,
	"scope": "",
	"id": 22,
	"avatar": ""
	"username": "admin"
	}
	```
