<%--
  Created by IntelliJ IDEA.
  User: kevincastro
  Date: 4/6/16
  Time: 3:15 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="subject"/>
</head>

<body>
    <div class="container subjectInfo">
       <div class="jumbotron row">
           <div class="col-lg-5 col-md-4">
               <img src="${request.contextPath}/images/Font-Book.png" class="img-rounded center-block" height="250" width="250">
               <h1 style="text-align: center">${c.name}</h1>
               <h2 style="text-align: center" >${c.location}</h2>
               <div class="row text-center">
                        <ul class="star-rating">
                           <li class="star" id="1">&#9734;</li>
                           <li class="star" id="2">&#9734;</li>
                           <li class="star" id="3">&#9734;</li>
                           <li class="star" id="4">&#9734;</li>
                           <li class="star" id="5">&#9734;</li>
                        </ul>
               </div>
               <div class="col-xs-12 display-rating" style="text-align: center">Tu voto fue de: </div>

           </div>
           <div class="col-lg-7 col-md-8" >
               <div class="col-xs-12" >
                   <h2>Descripción:</h2>
                   Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque facilisis feugiat justo in placerat. Integer sodales sapien turpis. Donec efficitur nec dolor ac ultricies. Nam ac blandit eros, id viverra eros. Cras pharetra congue elit ac ornare. Nam est nibh, congue et condimentum et, consequat eget nisi. Aliquam quis neque eget nunc varius auctor. Donec rutrum non risus sed suscipit. Vivamus et ornare ipsum. In ante quam, suscipit et massa et, scelerisque ultrices nulla. Donec fermentum est id odio pretium, dictum scelerisque felis imperdiet. Nam rutrum porttitor neque, eget suscipit ligula fringilla sed. Phasellus dapibus hendrerit sagittis. Nam vitae tincidunt ligula.
                   <br />
                   <h2>Temarios:</h2>
                   www.prueba.com
                   <br />
                   <h2>Profesores:</h2>
                   <g:each in="${c.teachers}" >
                        <a href="#"> ${it.name} </a>
                       <br />
                   </g:each>
               </div>
           </div>
        </div>
    </div>
    <div class="container">
        <div class="jumbotron row">
            <g:formRemote url="[controller:'Subject',action:'comment']" update="comentarios-nuevos" name="comentar">
                <div class="col-lg-9 col-md-8">
                    <div class="form-group">
                        <g:hiddenField name="id" value="${c.id}"></g:hiddenField>
                        <g:textArea name="body" value="" rows="5" class="form-control"></g:textArea>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4">
                    <button type="submit" form="comentar" class="btn btn-default btn-raised btn-block">Comentar</button>
                    <!--<button type="button" class="btn btn-default btn-raised btn-block" onclick="document.getElementById('imagenComentario').click()">
                        <i class="material-icons">photo</i>  Sube una imagen
                    </button>
                    <input type="file" id="imagenComentario" name="imagenComentario" style="display: none" /> -->
                </div>
            </g:formRemote>
        </div>
    </div>
    <div class="container">
            <div class="container col-lg-4">
                <div class="jumbotron">
                    <form role="form" id="filtro">
                        <h2>Busca tus comentarios:</h2>
                    </form>
                </div>
            </div>
          <div class="container col-lg-8">
              <h3>Comentarios</h3>
                <div class="jumbotron" id="container-comentarios">
                    <div class="row">
                        <div id="comentarios-nuevos">
                            <g:each in="${comments}" var="comentario">
                                <div class="comentario">
                                    <div class="col-sm-1">
                                        <div class="thumbnail">
                                            <img class="img-responsive user-photo" src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png">
                                        </div><!-- /thumbnail -->
                                    </div><!-- /col-sm-1 -->

                                    <div class="col-sm-11">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <strong>${comentario.author.name}</strong> <span class="text-muted">${comentario.date}</span>
                                            </div>
                                            <div class="panel-body">
                                                ${comentario.body}
                                            </div><!-- /panel-body -->
                                        </div><!-- /panel panel-default -->
                                    </div><!-- /sm-11 -->
                                </div>
                            </g:each>
                        </div>
                </div>
          </div>
    </div>
</body>
</html>