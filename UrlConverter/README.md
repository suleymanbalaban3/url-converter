# URL Converter


UrlConverter is a restful web service that convert templateshop.com links between web and mobile applications. <br> 
Web applications use URLs and mobile applications use deeplinks. Both applications use links to redriect specfic locatons inside applications. <br> 
When you want to redirect across applications, you should convert URLs to deeplinks or
deeplinks to URLs.

A quick example for URL and deeplink:<br>
Web URL: https://www.templateshop.com/butik/liste/erkek <br>
Equivalent deeplink: ty://?Page=Home&SectionId=2 <br>

###Tools
* Java Spring Boot Framework 
* Java Spring Boot JPA & Hibernate
* PostgreSQL
* JUnit
* Docker


### Guides
In docker-compose.yml file latest version of application is referenced from my docker hub repository <br> 
So only thing you have to do is just download and execute the docker-compose.yml file<br>
To run project Docker Desktop must be installed. Use docker-compose.yml file with the following command.
> docker-compose up

After execution, database connection will be set and application will be ready in http://localhost:8080 <br>


### Sample Request

Application consist of two different endpoints.
You can retrieve sample Postman collection from following url.
* https://www.getpostman.com/collections/222bf02bd444beae61d1

 * Web to Mobile Url's Conversion <br>
> POST http://localhost:8080/url-converter/web-url-to-mobile-url

> Json Request Body: <br> 
{
"url":"https://www.templateshop.com/caso/erkek-kol-saat-p-1925865?boutiqueId=439892"
}

 * Mobile to Web Url's Conversion

> POST http://localhost:8080/url-converter/mobile-url-to-web-url

> Json Request Body: <br>
{
"url":"ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064"
}




