# PoC : spring-boot, jaxb, export object tree in json 

- StreamingResponseBody  
- Configure time-out in application.properties : 
	spring.mvc.async.request-timeout=60000
- .vscode/launch.json : prompt for DB password instead of hardcoding it 
	{
		"version": "0.2.0",
		"configurations": [
		  {
			"type": "java",
			"name": "Spring Boot - Run",
			"request": "launch",
			"mainClass": "minfin.sandbox.SandboxApplication",
			"projectName": "sandbox",
			"env":{
			  "SPRING_DATASOURCE_PASSWORD":"${input:db2secret}",
			}
		  }
		],
		"inputs": [
		  {
			"type": "promptString",
			"id": "db2secret",
			"description": "DB2 SECRET",
			"default": "db2secret",
			"password": true,
		  }
		]
	  }
- src\main\resources\static\index.html
- src\main\resources\static\json-viewer.html

