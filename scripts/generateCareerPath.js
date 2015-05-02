
var distinctCompanies = db.getSiblingDB("pronet").getCollection("careerPath").distinct("career.company");
var levels = {"1":["software engineer","software developer","programmer analyst","application developer","Systems analyst"],
			  "2":["senior software engineer","staff software engineer","principal software engineer","member of technical staff","Senior Systems analyst"],
			  "3":["Technical Lead","Architect","solutions architect","senior architecture","systems architect"],
			  "4":["Head of engineering","Senior VP engineering","Director of Operations"],
			  "5":["CTO","CEO","Founder","Co-Founder","Senior Partner"]
			};

		
function generateCareerPaths(numberOfRecords){

	while(numberOfRecords!=0){
		var pronet = db.getSiblingDB("pronet");
		var careerPaths = db.getCollection("careerPath");
		var paths = [];
		var level = Math.ceil(Math.random()*levels.length);
	for(var index = 0; index < level; index++){
		var levelElment = Math.ceil(Math.random()*levels[index].length);
		paths.push({
			"title":level[levelElment]||level[levelElment-1],
			"company":distinctCompanies[Math.ceil(Math.random()*distinctCompanies.length)]		
		});
	}
	var careerPathForOneIndividual = {
	"fname" : "userFname-"+new Date().getTime().toString().substr(6,12),
	"lname" : "userLname-"+new Date().getTime().toString().substr(6,12),
	"permalink" : this.fname+"-"+this.lname,
	"career" :paths
    }
    careerPaths.insert(careerPathForOneIndividual);

	numberOfRecords-=1;
	}
	
}
