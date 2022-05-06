const checkAside = document.querySelector("input.none");
const openAsideBtn = document.querySelector("#openSideBar");
const asideButton = document.querySelector(".nav-top-expand");
const searchReposInput = document.getElementById("searchInputId");


function gerarID() {
  // Math.random should be unique because of its seeding algorithm.
  // Convert it to base 36 (numbers + letters), and grab the first 9 characters
  // after the decimal.
  // A função de gerarID pode ser usada para todas as coisas que podem ser cadastradas pelo usuario.
  // exemplo de ID gerado: _a7ny9bdqz
  return '_' + Math.random().toString(36).substr(2, 9);
}

function toggleAside(){
  let asideBar = document.querySelector("aside.aside-bar");
  let asideBarH1 = document.querySelectorAll("h1.aside-option");
  let mainArticle = document.querySelector("article.content");
  let isChecked = document.querySelector("input.none").checked;
  if(!isChecked){
      asideBar.style.width = "80px";
      mainArticle.style.width = "calc(100% - 80px - 1rem)";
      mainArticle.style.left = "80px";
      asideBarH1.forEach((e) => {
          e.style.display = "none";
      });
      document.querySelector("input.none").checked = true;
  }else{
      asideBar.style.width = "200px";
      mainArticle.style.width = "calc(100% - 200px - 1rem)";
      mainArticle.style.left = "200px";
      asideBarH1.forEach((e) => {
          e.style.display = "inline";
      });
      document.querySelector("input.none").checked = false;
  }    
}

asideButton.addEventListener("click", toggleAside);

function colocarConteudo() {
  let dropDown = document.getElementById("tipoMaterial");
  let opcao = dropDown.options[dropDown.selectedIndex].value;
  aparecerColocacao(opcao);
}
function aparecerColocacao(opcaoUsuario) {
  let conteudoColocar = document.getElementById("conteudoColocado");
  switch (opcaoUsuario) {
    case "texto":
      conteudoColocar.innerHTML = `<label for="disciplinaTexto">Disciplina deste conteúdo:</label><div class="input-group">
            <input type="text" id="disciplinaTexto" name="disciplinaTexto" class="input form-control"></div>
            <label for="tituloTexto">Título para seu texto:</label>
            <div class="input-group">
            <input type="text" id="tituloTexto" name="tituloTexto" class="input form-control"></div>
            <label class="d-block" for="corpoTexto">Coloque abaixo o texto principal:</label>
            <div class="input-group">
            <textarea type="text" id="corpoTexto" class="input form-control" rows="20" cols="45" classname="corpoTexto"></textarea>
            </div><br>
            <button onclick="desaparecerColocacaoTexto()" class="btn btn-success">Publicar</button><br><br>`;
      break;
    case "imagem":
      conteudoColocar.innerHTML = `
            <label for="disciplinaImagem">Disciplina deste conteúdo:</label><div class="input-group">
            <input type="text" id="disciplinaImagem" name="disciplinaImagem" class="input form-control"></div>
            <label for="tituloImagem">Título para a imagem:</label><div class="input-group">
            <input type="text" id="tituloImagem" name="tituloImagem" class="input form-control"></div>
            <label for="urlImagem">Insira a URL da imagem:</label><div class="input-group">
            <input type="text" id="urlImagem" name="urlImagem" class="input form-control"></div><br>
            <button onclick="desaparecerColocacaoImagem()" class="btn btn-success">Publicar</button><br><br>`;
      break;
    case "video":
      conteudoColocar.innerHTML = `<label for="disciplinaVideo">Disciplina deste conteúdo:</label><div class="input-group">
            <input type="text" id="disciplinaVideo" name="disciplinaVideo" class="input form-control"></div>
            <label for="tituloVideo">Título para o vídeo:</label><div class="input-group">
            <input type="text" id="tituloVideo" name="tituloVideo" class="input form-control"></div>
            <label for="urlVideo">Insira a parte final da URL do vídeo:</label><div class="input-group">
            <input type="text" id="urlVideo" name="urlVideo" class="input form-control"></div><br>
            <button onclick="desaparecerColocacaoVideo()" class="btn btn-success">Publicar</button><br><br>`;
      break;
  }

}
function desaparecerColocacaoTexto() {
  let conteudoColocar = document.getElementById("conteudoColocado");
  if (document.getElementById('disciplinaTexto').value.length == 0 || document.getElementById('tituloTexto').value.length == 0 || document.getElementById('corpoTexto').value.length == 0) {
    alert('Pelo menos um dos valores não foi preenchido!');
  } else {
    adicionarMaterialDb(document.getElementById('disciplinaTexto').value, document.getElementById('corpoTexto').value, document.getElementById('tituloTexto').value, '0');
  }

  conteudoColocar.innerHTML = "";

}
function desaparecerColocacaoImagem() {
  let conteudoColocar = document.getElementById("conteudoColocado");
  if (document.getElementById('disciplinaImagem').value.length == 0 || document.getElementById('tituloImagem').value.length == 0 || document.getElementById('urlImagem').value.length == 0) {
    alert('Pelo menos um dos valores não foi preenchido!');
  } else {
    adicionarMaterialDb(document.getElementById('disciplinaImagem').value, document.getElementById('urlImagem').value, document.getElementById('tituloImagem').value, '1');
  }
  conteudoColocar.innerHTML = "";
}
function desaparecerColocacaoVideo() {
  let conteudoColocar = document.getElementById("conteudoColocado");
  if (document.getElementById('disciplinaVideo').value.length == 0 || document.getElementById('tituloVideo').value.length == 0 || document.getElementById('urlVideo').value.length == 0) {
    alert('Pelo menos um dos valores não foi preenchido!');
  } else {
    adicionarMaterialDb(document.getElementById('disciplinaVideo').value, document.getElementById('urlVideo').value, document.getElementById('tituloVideo').value, '2');
  }
  conteudoColocar.innerHTML = "";
}
function adicionarMaterialDropdown() {
  let dropdown = document.getElementById("conteudoVer");
  let parser = localStorage.getItem("materialTodo");
  let materiais = JSON.parse(parser);
  dropdown.innerHTML = '<option hidden disabled>Selecione o conteúdo a ver:</option>';
  for (i = 0; i < materiais.material.length; i++) {
    let option = document.createElement('option');
    let text = document.createTextNode(materiais.material[i].titulo);
    option.appendChild(text);
    dropdown.appendChild(option);
    option.setAttribute('value', materiais.material[i].textoImagemVideo)
  }
}
function adicionarMaterialDb(disciplinaAdd, urlTextoAdd, tituloAdd, tipo) {
  let parser = localStorage.getItem("materialTodo");
  let materialAntigo = JSON.parse(parser);
  let novoMaterial = {
    disciplina: disciplinaAdd,
    url: urlTextoAdd,
    titulo: tituloAdd,
    textoImagemVideo: tipo
  }
  materialAntigo.material.push(novoMaterial);
  localStorage.setItem('materialTodo', JSON.stringify(materialAntigo));
  adicionarMaterialDropdown();
}
function mostrarConteudoTela() {
  let dropDown = document.getElementById("conteudoVer");
  let opcao = dropDown.options[dropDown.selectedIndex].value;
  let conteudoTela = document.getElementById('conteudoPostado');
  let parser = localStorage.getItem("materialTodo");
  let index = dropDown.selectedIndex - 1;
  let materiais = JSON.parse(parser);
  let IDUsuario = localStorage.getItem("usuarioAtual");
  let parser3 = localStorage.getItem("db");
  let objDados = JSON.parse(parser3);
  let usuarioPermissao = achaUsuarioAtual(IDUsuario, objDados);
  if (opcao != "3") {
    switch (opcao) {
      case '0':
        let textoHTML = `<div class="content-main"><h2 class="text-center">${materiais.material[index].titulo}</h2></div>
            <div class="my-3 mx-3"><p class="text-center">${materiais.material[index].url}</p></div>`;
        if (usuarioPermissao) {
          textoHTML += `<div class="d-flex justify-content-center"><button class="mx-3 my-3 btn btn-danger"onclick="retirarConteudo(${index})">Retirar Conteúdo</button></div><br><br>`;
        }
        conteudoTela.innerHTML = textoHTML;
        break;

      case '1':
        let textoHTML2 = `<div class="content-main"><h2 class="text-center">${materiais.material[index].titulo}</h2></div>
            <center><div class="my-3 mx-3"><img class="mx-auto img-fluid" src="${materiais.material[index].url}"></div><center>`
        if (usuarioPermissao) {
          textoHTML2 += `<div class="d-flex justify-content-center"><button class="mx-3 my-3 btn btn-danger" onclick="retirarConteudo(${index})">Retirar Conteúdo</button></div><br><br>`;
        }
        conteudoTela.innerHTML = textoHTML2;
        break;

      case '2':
        let name = 'https://www.youtube.com/embed/';
        name += materiais.material[index].url;
        let textoHTML3 = `<div class="content-main"><h2 class="text-center">${materiais.material[index].titulo}</h2></div>
            <center><iframe width="750" height="500" src=${name} title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe><center>`
        if (usuarioPermissao) {
          textoHTML3 += `<div class="d-flex justify-content-center"><button class="mx-3 my-3 btn btn-danger" onclick="retirarConteudo(${index})">Retirar Conteúdo</button></div><br><br>`;
        }
        conteudoTela.innerHTML = textoHTML3;
        break;
    }

  }
}

function retirarConteudo(index) {
  let conteudoTela = document.getElementById('conteudoPostado');
  conteudoTela.innerHTML = '';
  let parser = localStorage.getItem("materialTodo");
  let materiais = JSON.parse(parser);
  materiais.material.splice(index, 1);
  localStorage.setItem('materialTodo', JSON.stringify(materiais));
  adicionarMaterialDropdown();
}
function achaUsuarioAtual(Id, usuariodb) {
  for (i = 0; i < usuariodb.users.length; i++) {
    let usuario = usuariodb.users[i];
    if (usuario.user_id == Id) {
      return usuario.professor_flag;
    }
  }
  return false;//Se caiu aqui,usuario não está logado,e portanto não deve aparecer criar conteúdo
}
function criardbMensagem() {
  let parser = localStorage.getItem("db");
  let objDados = JSON.parse(parser);
  let id0 = objDados.users[0].user_id;
  let id1 = objDados.users[1].user_id;
  let id2 = objDados.users[2].user_id;
  let Mensagens = {
    mensagens: [
      { de: id0, para: id1, titulo: "Mensagem Teste-1", mensagem: "Teste corpo da mensagem 1.", id: gerarID() },
      { de: id1, para: id2, titulo: "Mensagem Teste-2", mensagem: "Teste corpo da mensagem 2.", id: gerarID() },
      { de: id2, para: id0, titulo: "Mensagem Teste-3", mensagem: "Teste corpo da mensagem 3.", id: gerarID() }
    ]
  }
  salvaDBMensagen(Mensagens);
}
function salvaDBMensagen(DB) {
  localStorage.setItem("dbMensagens", JSON.stringify(DB));
}
window.onload = function () {
  if (localStorage.getItem("primeiraVezDb") === null) {
    let conteudoDb = { material: [{ disciplina: 'Geografia', url: `O relevo corresponde às irregularidades contidas na superfície terrestre. Sua formação pode ter duas origens, provenientes de fatores endógenos (internos) e exógenos (externos).<br><br>
    <img src="img/relevos.jpeg" alt="Imagem representativa de relevos" width="400px" height="auto">
    <br><br>

    Os fatores internos da formação do relevo são o tectonismo e o vulcanismo. O tectonismo influencia na formação de relevo por meio das acomodações das placas litosféricas que podem ser de aproximação ou de afastamento.<br><br>
    
    Os movimentos da placas litosféricas são provocados pela quantidade de calor existente dentro da Terra, dando origem às correntes de convecção que podem ser convergentes e divergentes: a primeira quando as placas se chocam e a segunda quanto se afastam.<br><br>
    
    O processo de vulcanismo interfere na formação do relevo, pois quando existe uma grande pressão no interior da Terra, as camadas da crosta se rompem. De uma forma geral, o vulcanismo dá origem a duas formas de relevo: as montanhas e os planaltos.<br><br>
    
    Já os fatores exógenos (externos) formam o relevo por meio de erosões, que podem ser pluviais (provocadas pela água da chuva) e fluviais (provocadas pelas águas dos rios e mar). Nesses casos, o relevo sofre alterações, pois o escoamento das águas o desgasta dando a ele gradativamente novas formas.<br><br>
    
    As geleiras também promovem modificações no relevo através da erosão glacial, quando ocorrem avalanches e porções de rochas se desprendem, alterando, assim, o relevo do local. Por fim, existe a modificação do relevo por meio da ação dos ventos, denominada erosão eólica.<br><br>
    
    O homem também é um agente externo de transformação do relevo. Essas modificações são provenientes das atividades e das relações humanas. O homem, através do trabalho, transforma o relevo segundo os interesses econômicos ou mesmo para habitação.<br><br><strong>Fonte: mundoeducacao.uol.com.br</strong>`, titulo: 'Formação de relevos', textoImagemVideo: '0' },
   {disciplina: 'Matemática' , url: 'img/mate-a-matica.jpg' , titulo: 'Introdução à adição' , textoImagemVideo: '1'},
   {disciplina: 'História' , url: 'CTIs_RSPr84' , titulo: 'Idade Média' , textoImagemVideo: '2'}]}

    localStorage.setItem("materialTodo", JSON.stringify(conteudoDb));
    localStorage.setItem("primeiraVezDb", true);
  }  
    adicionarMaterialDropdown();
  
  if ((localStorage.getItem("db"))) {
    let parser = localStorage.getItem("db");
    let objDados = JSON.parse(parser);
    let idUsuario = localStorage.getItem("usuarioAtual");
    let eProfessor = achaUsuarioAtual(idUsuario, objDados);
    let dbMensagens = localStorage.getItem("dbMensagens");
    if (!dbMensagens) {
      criardbMensagem(idUsuario);
    }
    if (eProfessor) {
      document.getElementById("btnConteudo").addEventListener("click", colocarConteudo);

    } else {
      document.getElementById('professorApenas').innerHTML = '';
    }
  } else {
    document.getElementById('professorApenas').innerHTML = '';
  }
  if (localStorage.getItem("primeiraVezDb") == true) {

  }
}
document.getElementById("btnConteudoMostrar").addEventListener("click", mostrarConteudoTela);