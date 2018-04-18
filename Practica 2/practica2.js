var api_key= "8266454cc4bfb6161d3a5f3a27e8f1ee";
var user_id;

$(function(){					//Importante esta función, cuando se carga la página es lo que se ejecuta
	$("#formulario").submit(mostrar);
	//$("#boton").click(mostrar);
})

/*
	PASOS A REALIZAR:
	- Crear formulario de entrada con los criterios
	- Poner como id de cada input el nombre del atributo para la URL
	- Hacer $("#formulario").serialize() para que lo convierta a pares clave=valor
	- Añadir a la URL
*/

function mostrar() {

	event.preventDefault();	//Para evitar que haga el uso por defecto de submit
	
	//Tratamiento de user_id (OBLIGATORIO)
	user_id = $("#user_id").val();
	console.log("user_id: " + user_id);
	var user_idURL = "";
	if ($("#user_id").val() != "") {
		user_idURL = "&user_id=" + user_id;
	}

	//Tratamiento de min_taken_date
	var min_taken_dateString = $("#min_taken_date").val();
	var min_taken_date= encodeURI($("#min_taken_date").val());
	console.log("min_taken_date:" + min_taken_date);
	var min_taken_dateURL = "";
	if ($("#min_taken_date").val() != "") {
		min_taken_dateURL = "&min_taken_date=" + min_taken_date +
			"&extras=" + encodeURI("tags,date_taken,date_upload,views");

	}

	//Tratamiento de max_taken_date
	var max_taken_dateString = $("#max_taken_date").val();
	var max_taken_date= encodeURI($("#max_taken_date").val());
	console.log("max_taken_date:" + max_taken_date);
	var max_taken_dateURL = "";
	if ($("#max_taken_date").val() != "") {
		max_taken_dateURL = "&max_taken_date=" + max_taken_date +
			"&extras=" + encodeURI("tags,date_taken,date_upload,views");
	}


	//Tratamiento de min_upload_date
	var min_upload_dateString = $("#min_upload_date").val();
	var min_upload_date= encodeURI($("#min_upload_date").val());
	console.log("min_upload_date:" + min_upload_date);
	var min_upload_dateURL = "";
	if ($("#min_upload_date").val() != "") {
		min_upload_dateURL = "&min_upload_date=" + min_upload_date +
			"&extras=" + encodeURI("tags,date_taken,date_upload,views");
	}

	//Tratamiento de max_upload_date
	var max_upload_dateString = $("#max_upload_date").val();
	var max_upload_date= encodeURI($("#max_upload_date").val());
	console.log("max_upload_date:" + max_upload_date);
	var max_upload_dateURL = "";
	if ($("#max_upload_date").val() != "") {
		max_upload_dateURL = "&max_upload_date=" + max_upload_date +
			"&extras=" + encodeURI("tags,date_taken,date_upload,views");
	}

	//Incluir extras al final ????

	//Creación de la url
	var url = 'https://api.flickr.com/services/rest/?&method=flickr.people.getPhotos&api_key=' + api_key + user_idURL + min_taken_dateURL + 
		max_taken_dateURL + min_upload_dateURL + max_upload_dateURL + '&format=json&nojsoncallback=1';
	console.log(url);

	//Borra el resultado de la anterior consulta
	$("#listaFotos").html("");

	$.getJSON(url, mostrar_fotos)
}

function mostrar_fotos(info){
	var i;
	for (i=0;i<info.photos.photo.length;i++) {
	   var item = info.photos.photo[i];
	   var url = 'https://farm' + item.farm + ".staticflickr.com/" + item.server
		          + '/' + item.id + '_' + item.secret + '_m.jpg';
	   console.debug(url);
	   var indice = i.toString();

	   $("#listaFotos").append($("<div/>").attr('id', indice));
	   $("#"+indice).append($("<img/>").attr("src",url));
	   $("#"+indice).append($("<p/>").html("user_id: " + user_id ));

	   if ( ($("#min_taken_date").val() != "") || ($("#max_taken_date").val() != "") ) {	//Si se utiliza un criterio sobre datetaken
	   		$("#"+indice).append($("<p/>").append(", taken_date: " + item.datetaken));		//Incluimos la información sobre datetaken
	   }

	   
    }
}
/*	PUEDE SER INNECESARIO SI AÑADIMOS EXTRAS
    //Se hace después, ya que las peticiones son asíncronas --> Lo pone todo con el valor de índice 4
    for (i=0;i<info.photos.photo.length;i++) {
    	item = info.photos.photo[i];
	   	indice = i.toString();
	   	//Petición para obtener los datos de la foto
    	$.getJSON("https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=" + api_key + 
	   	"&photo_id=" + item.id + '&format=json&nojsoncallback=1', indice, function (data) {
	   		$("#"+indice).append($("<p/>").html("user_id: " + user_id + ", taken_date: " + data.photo.dates.taken));
	   	})
    }
}
*/
/*
 if($("#direccion").val() == ""){
        alert("El campo Dirección no puede estar vacío.");
        $("#direccion").focus();
        return false;
    }
 */