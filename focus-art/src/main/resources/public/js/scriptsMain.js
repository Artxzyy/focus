const checkAside = document.querySelector("input.none");
const openAsideBtn = document.querySelector("#openSideBar");
const asideButton = document.querySelector(".nav-top-expand");
const profileDiv = document.querySelector(".profile-info");
const projectsDiv = document.querySelector(".projects");
const searchReposInput = document.getElementById("searchInputId");

/**
 * @brief Acha e retorna o id do usuario atual
 * 
 * @param id id do usuario
 * @param obj de dados de todos os usuarios
 * @return id do usuario logado
 */
function achaUsuarioAtualId(Id,usuariodb){
    for(i=0;i<usuariodb.users.length;i++){
        let usuario = usuariodb.users[i];
        if(usuario.user_id==Id){
            return usuario.user_id;
        }
    }
    return false;//Se caiu aqui,usuario não está logado,e portanto não deve aparecer criar conteúdo
  }
  function achaNomePorId(ID, DB) {
    let resposta = "erro";
    for (i = 0; i < DB.users.length; i++) {
        let usuario = DB.users[i];
        if (usuario.user_id == ID) {
            resposta = usuario.name;
            return resposta;
        }
    }
    return resposta;
}
window.onload = function(){
    if(localStorage.getItem("atividadesTodas")){
        let parser = localStorage.getItem("atividadesTodas");
        let atividades = JSON.parse(parser);
        let tamanho = atividades.atividade.length;
        let tela = document.getElementById("tela");
        let i = 0;
        for(i; i < tamanho; i = i + 1){
            tela.innerHTML = tela.innerHTML + `
            <div class="card-ex">
              <div class="card-title">
                <h1 class="card-title">Atividade - ${atividades.atividade[i].disciplina}</h1>
              </div>
              <div class="card-body">
                <strong><p class="card-text">${atividades.atividade[i].titulo}</p></strong>
                <p class="card-text">Matéria: ${atividades.atividade[i].materia}</p>
                <a href="atividade.html"><button>Ver Atividade</button></a>
              </div>
            </div>
            `;
        }
    }
    if(localStorage.getItem("materialTodo")){
        let parser = localStorage.getItem("materialTodo");
        let materiais = JSON.parse(parser);
        let tamanho = materiais.material.length;
        let tela = document.getElementById("tela");
        let i = 0;
        for(i; i < tamanho; i = i + 1){
            tela.innerHTML = tela.innerHTML + `
            <div class="card-ex">
              <div class="card-title">
                <h1 class="card-title">Conteúdo - ${materiais.material[i].disciplina}</h1>
              </div>
              <div class="card-body">
                <p class="card-text">Matéria: ${materiais.material[i].titulo}</p>
                <a href="conteudo.html"><button>Ver Conteúdo</button></a>
              </div>
            </div>
            `;
        }
    }
    if(localStorage.getItem("dbMensagens")){
        let parser1 = localStorage.getItem("db");
        let objDados = JSON.parse(parser1)
        let usuarioAtualId = localStorage.getItem("usuarioAtual");
        let parser = localStorage.getItem("dbMensagens");
        let mensagensJs = JSON.parse(parser);
        let tamanho = mensagensJs.mensagens.length;
        let tela = document.getElementById("tela");
        let i = 0;
        for(i; i < tamanho; i = i + 1){
            if(mensagensJs.mensagens[i].para == achaUsuarioAtualId(usuarioAtualId, objDados)){
                tela.innerHTML = tela.innerHTML + `
                <div class="card-ex">
                    <div class="card-title">
                        <h1 class="card-title">Mensagem de "${achaNomePorId(mensagensJs.mensagens[i].de,objDados)}"</h1>
                    </div>
                  <div class="card-body">
                    <p class="card-text">Assunto: ${mensagensJs.mensagens[i].titulo}</p>
                    <a href="mensagem.html"><button>Mensagen</button></a>
                  </div>
                </div>
                `;
            }
        }
    }
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

