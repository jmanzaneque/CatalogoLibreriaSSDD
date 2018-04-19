var api_key= "8266454cc4bfb6161d3a5f3a27e8f1ee";
var user_id;

$(function(){					//Importante esta función, cuando se carga la página es lo que se ejecuta
	$("#formulario").submit(mostrar);
	$("#formulario").submit(zoomFotos);
})

/* calendar */
$( function() {
	$ ("#min_taken_date").datepicker({
		changeMonth:true, 
		changeYear: true});
});
$( function() {
	$ ("#max_taken_date").datepicker({
		changeMonth:true, 
		changeYear: true});
});
$( function() {
	$ ("#min_upload_date").datepicker({
		changeMonth:true, 
		changeYear: true});
});
$( function() {
	$ ("#max_upload_date").datepicker({
		changeMonth:true, 
		changeYear: true});
});

function zoomFotos(event){
	event.preventDefault();	//Para evitar que haga el uso por defecto de submit
	$("#listaFotos").each(function(){
		$($(this.id)).elevateZoom({
  			cursor: "crosshair",  // Para que se muestre una cruz al apoyar el cursor sobre la imagen
 			zoomWindowFadeIn: 500, // El tiempo que tarda en mostrar el zoom al apoyar el cursor sobre la imagen
 			zoomWindowFadeOut: 750 // El tiempo que tarda en desaparecer el zoom al sacar el cursor sobre la imagen
		}
		);
	})
	;



}

function mostrar(event) {

	event.preventDefault();	//Para evitar que haga el uso por defecto de submit
	
	console.log("PARÁMETROS DE LA CONSULTA");
	//Tratamiento de user_id (OBLIGATORIO)
	user_id = $("#user_id").val();
	console.log("user_id: " + user_id);
	var user_idURL = "";
	if ($("#user_id").val() != "") {
		user_idURL = "&user_id=" + user_id;
	}

	//LAS FECHAS SE ESCRIBEN DE LA FORMA mm/dd/aaaa


	//Tratamiento de min_taken_date

	var min_taken_dateString = $("#min_taken_date").val();
	var min_taken_date= encodeURI($("#min_taken_date").val());
	console.log("min_taken_date:" + min_taken_date);
	var min_taken_dateURL = "";
	if ($("#min_taken_date").val() != "") {
		min_taken_dateURL = "&min_taken_date=" + min_taken_date;

	}

	//Tratamiento de max_taken_date
	var max_taken_dateString = $("#max_taken_date").val();
	var max_taken_date= encodeURI($("#max_taken_date").val());
	console.log("max_taken_date:" + max_taken_date);
	var max_taken_dateURL = "";
	if ($("#max_taken_date").val() != "") {
		max_taken_dateURL = "&max_taken_date=" + max_taken_date;
	}


	//Tratamiento de min_upload_date
	var min_upload_dateString = $("#min_upload_date").val();
	var min_upload_date= encodeURI($("#min_upload_date").val());
	console.log("min_upload_date:" + min_upload_date);
	var min_upload_dateURL = "";
	if ($("#min_upload_date").val() != "") {
		min_upload_dateURL = "&min_upload_date=" + min_upload_date;
	}

	//Tratamiento de max_upload_date
	var max_upload_dateString = $("#max_upload_date").val();
	var max_upload_date= encodeURI($("#max_upload_date").val());
	console.log("max_upload_date:" + max_upload_date);
	var max_upload_dateURL = "";
	if ($("#max_upload_date").val() != "") {
		max_upload_dateURL = "&max_upload_date=" + max_upload_date;
	}

	//Tratamiento de views
	var views = Number($("#views").val());
	console.log("views: " + views);

	//Tratamiento de title
	var title = $("#title").val();
	console.log("title: " + title);

	//Tratamiento de description
	var description = $("#description").val();
	console.log("description:" + description);


	//Creación de la url
	var url = 'https://api.flickr.com/services/rest/?&method=flickr.people.getPhotos&api_key=' + api_key + user_idURL + min_taken_dateURL + 
		max_taken_dateURL + min_upload_dateURL + max_upload_dateURL + "&extras=" + encodeURI("description,tags,date_taken,date_upload,views") + '&format=json&nojsoncallback=1';
	console.log(url);

	//Borra el resultado de la anterior consulta
	$("#listaFotos").html("");

	$.getJSON(url, mostrar_fotos);
}

function mostrar_fotos(info){
	var i;
	//Para cada foto devuelta en el JSON, una vez ha pasado los filtros de min_taken_date, max_taken_date, min_upload_date y max_upload date
	for (i=0;i<info.photos.photo.length;i++) {
	   var item = info.photos.photo[i];

	   //Creación url foto
	   var url = 'https://farm' + item.farm + ".staticflickr.com/" + item.server
		          + '/' + item.id + '_' + item.secret + '_m.jpg';
	   var url2 = 'https://farm' + item.farm + ".staticflickr.com/" + item.server
		          + '/' + item.id + '_' + item.secret + '.jpg';
	   console.debug(url);			//Saca la url por consola
	   var indice = i.toString();

	   //Control de parámetros views, description y title 
	   //(min_taken_date, max_taken_date, min_upload_date y max_upload date se han filtrado en la petición HTTP)
	   var views = Number($("#views").val());
	   var containViews = $("#views").val() != "";
	   var pasaFiltroViews = (!containViews) ||  (containViews && (item.views >= views));	//True si el campo views está vacío o si item.views es mayor al parámetro views.
	   
	   var containTitle = $("#title").val() != "";
	   var includesTitle = isExist(item.title, $("#title").val());
	   var pasaFiltroTitle = (!containTitle) || (containTitle && includesTitle);
	   
	   var containDescription = $("#description").val() != "";
	   var includesDescription = isExist(item.description._content, $("#description").val());
	   var pasaFiltroDescription = (!containDescription) || (containDescription && includesDescription);

	   if (pasaFiltroDescription && pasaFiltroTitle && pasaFiltroViews) { 	//Si pasa los tres filtros --> Añadir foto al resultado de la búsqueda
	   		$("#listaFotos").append($("<div/>").attr('id', indice));
	   		$("#"+indice).append($("<a/>").append($("<img/>").attr("src",url)));
			$("#"+indice).append($("<p/>").html("user_id: " + user_id ));

		   if ( ($("#min_taken_date").val() != "") || ($("#max_taken_date").val() != "") ) {	//Si se utiliza un criterio sobre datetaken
		   		$("#"+indice).append($("<p/>").append(", taken_date: " + item.datetaken));		//Incluimos la información sobre datetaken
		   }

		   if ( ($("#min_upload_date").val() != "") || ($("#max_upload_date").val() != "") ) {	//Si se utiliza un criterio sobre dateupload
		   		//CORREGIR DATE
		   		var time = Number(item.dateupload);
		   		console.log("Tiempo en segundos: " + time);
		   		var uploaddate = new Date(time);

		   		var upload_dateString = 
		   		console.log("Fecha de upload: " + upload_dateString );
		   		$("#"+indice).append($("<p/>").append(", upload_date: " + upload_dateString));		//Incluimos la información sobre dateupload
		   }

		   if (containViews) {		//Si se ha aplicado un filtro views
		   		$("#"+indice).append($("<p/>").append(", views: " + item.views));		//Incluimos la información sobre views
		   }

		   if (containTitle) {		//Si se ha aplicado un filtro title
		   		$("#"+indice).append($("<p/>").append(", title: " + item.title));		//Incluimos la información sobre title
		   }

		   if (containDescription) {		//Si se ha aplicado un filtro description
		   		$("#"+indice).append($("<p/>").append(", description: " + item.description._content));		//Incluimos la información sobre description
		   }


	   } //End if

	   	   
    } //End for
}

function isExist(mainString, substring) {
	if (mainString.indexOf(substring) != -1) {
	    return true;
	}
	else {
	    return false;
	}
}
<<<<<<< HEAD

/*
 if($("#direccion").val() == ""){
        alert("El campo Dirección no puede estar vacío.");
        $("#direccion").focus();
        return false;
    }

    function isExist(mainString, substring) {

if (mainString.indexOf(substring) != -1) {
    return true;
}
else {
    return false;
}
}
 */
=======
>>>>>>> 2d54137ec446b7e496c0254304b89e2e1d25a85f