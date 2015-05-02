var permalinks=db.companies.distinct("relationships.person.permalink");
var relationships=db.companies.distinct("relationships");
var careerPath = {};

var companies = db.companies.find({},{name:1,relationships:1});
companies.forEach(function(company){
	var relationships = company.relationships;
	if(relationships){ //ignore the empty relationships
		relationships.forEach(function(rel){
			var permalink = rel.person.permalink;
			if(permalink){
				if(careerPath[permalink]){
					var path = careerPath[permalink];
					path.career.push({
						"title":rel.title,
						"past":rel.is_past,
						"company":company.name
					});

				}else{
					var positions = [];
					positions.push({
						"title":rel.title,
						"past":rel.is_past,
						"company":company.name
					});	
					careerPath[permalink] = {
						fname:rel.person.first_name,
						lname:rel.person.last_name,
						permalink:permalink,
						career:positions
					}
				}
			}
	});
	}
});

/*permalinks.forEach(function(plink){
	relationships.forEach(function(rel){
			if(rel.person.permalink==plink){
				if(careerPath[plink]){
					var path = careerPath[plink];
					path.career.push({
						"title":rel.title,
						"past":rel.is_past
					});

				}else{
					var positions = [];
					positions.push({
						"title":rel.title,
						"past":rel.is_past
					});	
					careerPath[plink] = {
						fname:rel.person.first_name,
						lname:rel.person.last_name,
						permalink:plink,
						career:positions
					}
				}
			}
	});
});
*/
Object.keys(careerPath).forEach(function(permalink){
	db.careerPath.insert(careerPath[permalink]);
});

//printjson(careerPath);
//var cp={};Object.keys(careerPath).forEach(function(key){if(careerPath[key].career.length>1){cp[key]=careerPath[key];}});