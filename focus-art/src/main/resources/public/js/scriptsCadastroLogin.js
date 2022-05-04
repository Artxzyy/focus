/* begin constants */


    /* begin general constants */

const body = document.querySelector("body"); // body of the page

    /* end general constants */

    /* begin register constants */

const registerModal = document.querySelector("article#registerModalId"); // register modal itself
const registerLink = document.querySelector("a#registerLink"); // link that leads to the register modal (on login form)
const registerBtn = document.querySelector("input#registerBtn"); // button that confirms the new user being created
const closeRegisterModalBtn = document.querySelectorAll("span.close")[0]; // close button of the modal register
const registerNav = document.querySelector("a#registerNav"); // close button of the modal register

const inputUsernameRegister = document.querySelector("input#usernameRegisterId"); // input username of modal register
const inputEmailRegister = document.querySelector("input#emailRegisterId"); // input email of modal register
const inputPassword1Register = document.querySelector("input#password1RegisterId"); // input first password of modal register
const inputPassword2Register = document.querySelector("input#password2RegisterId"); // input second password of modal register
const isProfessor = document.querySelector("input#isProfessor"); // radiobutton that says "yes" in case the user is a professor - boolean value

const registerValidationDiv = document.querySelector("div#registerValidationId"); // the validation status of the register modal input
const spanUsernameRegister = document.querySelector("span#spanUsernameRegister"); // text of username register validation
const iconUsernameRegister = document.querySelector("i#iconUsernameRegister"); // icon of username register validation
const spanEmailRegister = document.querySelector("span#spanEmailRegister"); // text of email register validation
const iconEmailRegister = document.querySelector("i#iconEmailRegister"); // icon of email register validation
const spanPasswordRegister = document.querySelector("span#spanPasswordRegister"); // text of password register validation
const iconPasswordRegister = document.querySelector("i#iconPasswordRegister"); // icon of password register validation

    /* end register constants */

    /* begin login constants */

const submitLoginBtn = document.querySelector("input#submitId"); // submit button of login
const formLogin = document.querySelector("form#loginFormId");
const inputUsername = document.querySelector("input#usernameInputId"); // input of login username
const inputPassword = document.querySelector("input#passwordInputId"); // input of login password
const submitErrorDiv = document.querySelector("div#submitErrorId"); // div that contains the error message
const rememberMeCheckBox = document.querySelector("input#rememberId");

    /* end login constants */

    /* begin forget password constants */

const forgetPasswordLink = document.querySelector("a#forgetPasswordLinkId");
const forgetPasswordModal = document.querySelector("article#forgetPasswordModalId");
const closeForgetPasswordModalSpan = document.querySelectorAll("span.close")[1]; // close button of the modal forget password

const forgetPasswordEmailInput = document.querySelector("input#forgetPasswordEmailId");
const forgetPasswordPassword1Input = document.querySelector("input#forgetPasswordNewPassword1Id");
const forgetPasswordPassword2Input = document.querySelector("input#forgetPasswordNewPassword2Id");

const submitForgetPassword = document.querySelector("input#changePasswordSubmitId");
const divErrorForgetPassword = document.querySelector("div.error");

const spanEmailForget = document.querySelector("span#spanEmailForget");
const spanPasswordBefore = document.querySelector("span#spanPasswordBefore");
const spanPasswordNow = document.querySelector("span#spanPasswordNow");

    /* end forget password constants */

    

/* end constants */

/**
 * Change the visual of the email input on forget password form
 */
function validateInputEmailForgetPassword(){
    let forgetPasswordEmail = forgetPasswordEmailInput.value;
    if(validateEmail(forgetPasswordEmail)){
        forgetPasswordEmailInput.classList.remove("border-error", "placeholder-red-400");
        forgetPasswordEmailInput.classList.remove("placeholder-red-400");
        forgetPasswordEmailInput.classList.add("ring-gray-300");
    }else{
        forgetPasswordEmailInput.classList.add("border-error");
        forgetPasswordEmailInput.classList.add("placeholder-red-400");
        forgetPasswordEmailInput.classList.remove("ring-gray-300");
    }
}

/**
 * Change the visual of the first password input on forget password form
 */
function validateInputPassword1ForgetPassword(){
    let forgetPasswordPassword1 = forgetPasswordPassword1Input.value;
    if(validatePassword(forgetPasswordPassword1)){
        forgetPasswordPassword1Input.classList.remove("border-error", "placeholder-red-400");
        forgetPasswordPassword1Input.classList.remove("placeholder-red-400");
        forgetPasswordPassword1Input.classList.add("ring-gray-300");
    }else{
        forgetPasswordPassword1Input.classList.add("border-error");
        forgetPasswordPassword1Input.classList.add("placeholder-red-400");
        forgetPasswordPassword1Input.classList.remove("ring-gray-300");
    }
}

/**
 * Change the visual of the second password input on forget password form
 */
function validateInputPassword2ForgetPassword(){
    let forgetPasswordPassword2 = forgetPasswordPassword2Input.value;
    if(validatePassword(forgetPasswordPassword2)){
        forgetPasswordPassword2Input.classList.remove("border-error", "placeholder-red-400");
        forgetPasswordPassword2Input.classList.remove("placeholder-red-400");
        forgetPasswordPassword2Input.classList.add("ring-gray-300");
    }else{
        forgetPasswordPassword2Input.classList.add("border-error");
        forgetPasswordPassword2Input.classList.add("placeholder-red-400");
        forgetPasswordPassword2Input.classList.remove("ring-gray-300");
    }
}

/**
 * Make the submit forget password form button be disabled if not correctly writen
 */
function validateSubmitForgetPassword(){
    let forgetPasswordEmail = forgetPasswordEmailInput.value;
    let forgetPasswordPassword1 = forgetPasswordPassword1Input.value;
    let forgetPasswordPassword2 = forgetPasswordPassword2Input.value;

    if(validateEmail(forgetPasswordEmail) && validatePassword(forgetPasswordPassword1) && validatePassword(forgetPasswordPassword2)){
        submitForgetPassword.disabled = false;
    }else{
        submitForgetPassword.disabled = true;
    }
}

/**
 * Validate if the forget password is valid and returns true or false
 * @param {string} email_input email input value of forget password form
 * @param {string} new_password1 first password input value of forget password form
 * @param {string} new_password2 second password input value of forget password form
 * @returns true if valid, false if not
 */
function validateForgetPassword(email_input, new_password1, new_password2){
    let isValid = false;
    if(localStorage.getItem("db")){
        let db = JSON.parse(localStorage.getItem("db"));
        if(new_password1 === new_password2){
            if(validatePassword(new_password1)){
                let i = 0;
                let bool_password = false;
                let bool_email = false;
                while(i < db.users.length && !isValid){
                    if(email_input === db.users[i].email){
                        bool_email = true;
                        if(new_password1 !== db.users[i].password){
                            isValid = true;
                            bool_password = true;
                        }
                    }
                    i = i + 1;
                }
                if(!bool_email){
                    spanPasswordBefore.style.display = "none";
                    spanEmailForget.style.display = "block";
                    spanPasswordNow.style.display = "none";
                }else{
                    if(!bool_password){
                        spanPasswordBefore.style.display = "block";
                        spanEmailForget.style.display = "none";
                        spanPasswordNow.style.display = "none";
                    }
                }
            }
        }else{
            spanPasswordBefore.style.display = "none";
            spanEmailForget.style.display = "none";
            spanPasswordNow.style.display = "block";
        }
    }
    return isValid;
}


/**
 * search for a user by email and change his password if it is valid and if the user was found
 * @param {string} email_input email of the user you want to find
 * @param {string} new_password new password of the user 
 * @returns the changed user data
 */
function searchUserByEmail(email_input, new_password){
    if(localStorage.getItem("db")){
        let db = JSON.parse(localStorage.getItem("db"));
        let i = 0;
        let bool = true;
        let correct_user = {};
        while(i < db.users.length && bool){

            if(db.users[i].email === email_input){
                correct_user = db.users[i];
                var new_user = {
                    user_id: correct_user.user_id,
                    name: correct_user.name,
                    username: correct_user.username,
                    email: correct_user.email,
                    password: new_password, // the alteration
                    professor_flag: correct_user.professor_flag,
                    admin_flag: correct_user.admin_flag
                }
                db.users.splice(i, 1, new_user);
                bool = false;
            }
            i = i + 1;
        }
        if(!bool){ 
            localStorage.setItem("db", JSON.stringify(db));
        }else{
            spanPasswordBefore.style.display = "none";
            spanEmailForget.style.display = "block";
            spanPasswordNow.style.display = "none";
        }
    }
    return new_user;
}

/**
 * search for an user object by the username
 * @param {string} username_input username of the user
 * @returns the user or null
 */
function searchUserByUsername(username_input){
    if(localStorage.getItem("db")){
        let db = JSON.parse(localStorage.getItem("db"));
        let i = 0;
        let bool = true;
        while(i < db.users.length && bool){
            if(db.users[i].username === username_input){
                var correct_user = db.users[i];
                bool = false;
            }
            i = i + 1;
        }
    }
    return correct_user;
}

/**
 * Takes the input values of the change password form, if it is valid, change his password
 */
function changePassword(){
    let email_input = forgetPasswordEmailInput.value;
    let new_password1 = forgetPasswordPassword1Input.value;
    let new_password2 = forgetPasswordPassword2Input.value;
    if(validateForgetPassword(email_input, new_password1, new_password2)){
        searchUserByEmail(email_input, new_password2);
        forgetPasswordEmailInput.value = "";
        forgetPasswordPassword1Input.value = "";
        forgetPasswordPassword2Input.value = "";
        spanPasswordBefore.style.display = "none";
        spanEmailForget.style.display = "none";
        spanPasswordNow.style.display = "none";
    }
}

/**
 * Generete a new id to the entity assigned
 * @returns an unique id
 */
function generateID() { // not my function
    // Math.random should be unique because of its seeding algorithm.
    // Convert it to base 36 (numbers + letters), and grab the first 9 characters
    // after the decimal.
    // example of returned id: _a7ny9bdqz
    return '_' + Math.random().toString(36).substr(2, 14);
}

/**
 * if LS doesn't exist, then create it and put the first user. If it does, don't do anything. Run this function on window.onload
 */
function startLocalStorage(){
    if(!localStorage.getItem("db")){
        let first_user = {
            users: [{user_id: generateID(), name: "Arthur", username: "arthur", email: "arthur@gmail.com", password: "12345678", professor_flag: true, admin_flag: true},
                    {user_id: generateID(), name: "Rodrigues", username: "rodrigues", email: "rodrigues@gmail.com", password: "12345678", professor_flag: true, admin_flag: true},
                    {user_id: generateID(), name: "Soares", username: "soares", email: "soares@gmail.com", password: "12345678", professor_flag: true, admin_flag: true}],
        };
        localStorage.setItem("db", JSON.stringify(first_user));
    }
}

/**
 * create new user and adds it to the localStorage
 */
function createNewUser(username_value, email_value, password_value, professor_flag_value){
    let parser = localStorage.getItem("db");
    let db = JSON.parse(parser);
    let newUser = {
        user_id: generateID(),
        name: "", // defined on the profile
        username: username_value,
        email: email_value,
        password: password_value,
        professor_flag: professor_flag_value,
        admin_flag: false
    };
    db.users.push(newUser);
    localStorage.setItem("db", JSON.stringify(db));
    
}

/**
 * Validate all the data of the register form and in case of error, it doesn't create it
 */
function prepareNewUser(){
    let username = inputUsernameRegister.value;
    let password1 = inputPassword1Register.value;
    let password2 = inputPassword2Register.value;
    let email = inputEmailRegister.value;
    let db = JSON.parse(localStorage.getItem("db"));

    if(validateUsername(username) && validatePasswordsRegister(password1, password2) && validateEmail(email)){
        let bool_username = true;
        let bool_email = true;
        for(let i = 0; i < db.users.length; i = i + 1){
            if(username === db.users[i].username){
                bool_username = false;
            }
            if(email === db.users[i].email){
                bool_email = false;
            }
        }
        if(bool_username && bool_email){
            let professor_flag = isProfessor.checked;
            createNewUser(username, email, password1, professor_flag);
            inputUsernameRegister.value = "";
            inputPassword1Register.value = "";
            inputPassword2Register.value = "";
            inputEmailRegister.value = "";
            alert("Your account was created succesfully");
            closeRegisterModal;
        }else{
            if(!bool_username){
                if(!bool_email){
                    alert("This username and this e-mail is already being used.");
                }else{
                    alert("This username is already being used.");
                }
            }else{
                alert("This e-mail is already being used.");
            }
        }
        
    }
}

/**
 * Validate username, it needs to be bigger than 2 chars
 * @param {*} string username
 * @returns true if it is bigger than 2 chars, false if not
 */
function validateUsername(string){ 
    return (string.length > 2);
}

/**
 * Validate password, it needs to be bigger than 5 chars
 * @param {*} string password
 * @returns true if it is bigger than 5 chars, false if not
 */
function validatePassword(string){ // validate password
    return (string.length > 5);
}

/**
 * Change the style of the username and the password input in the login form when the user do something wrong
 */
function validateUsernameAndPassword(){
    submitLoginBtn.disabled = true;
    if(validateUsername(inputUsername.value)){
        inputUsername.classList.remove("border-error", "placeholder-red-400");
        inputUsername.classList.add("ring-gray-300");
        if(!validatePassword(inputPassword.value)){
            inputPassword.classList.add("border-error", "placeholder-red-400");
            inputPassword.classList.remove("ring-gray-300");
        }
        else{
            inputPassword.classList.remove("border-error", "placeholder-red-400");
            inputPassword.classList.add("ring-gray-300");
            submitLoginBtn.disabled = false;
        }
        
    }
    else{
        inputUsername.classList.add("border-error", "placeholder-red-400");
        inputUsername.classList.remove("ring-gray-300");
        if(!validatePassword(inputPassword.value)){
            inputPassword.classList.add("border-error", "placeholder-red-400");
            inputPassword.classList.remove("ring-gray-300");
        }
        else{
            inputPassword.classList.remove("border-error", "placeholder-red-400");
            inputPassword.classList.add("ring-gray-300");
            
        }
        submitLoginBtn.disabled = true;
    }
}

function validateSubmitLogin(){
    submitErrorDiv.innerHTML = "";
    if(localStorage.getItem("db")){ // if localStorage was created
        let parser = localStorage.getItem("db");
        let ls_users = JSON.parse(parser);
        let user_correct = false;
        for(let i = 0; i < ls_users.users.length; i = i + 1){
            if(inputUsername.value === ls_users.users[i].username && inputPassword.value === ls_users.users[i].password){
                user_correct = true;
                sessionStorage.setItem("focus.ss.user", JSON.stringify({username: inputUsername.value, password: inputPassword.value}));
            }
        }
        if(user_correct){ // if the user did everything right, go to the next page
            window.location = "main.html";
            alert("");
        }else{ // if not, show an error message
            let errorMsg = document.createElement("span");
            let errorIcon = document.createElement("i");
            errorIcon.classList.add("fa-solid", "fa-circle-xmark"); // <i class="fa-solid fa-circle-xmark"></i>
            errorMsg.textContent = "The username or the password is wrong.";
            submitErrorDiv.append(errorIcon, errorMsg);
            if(sessionStorage.getItem("focus.ss.user")){
                sessionStorage.removeItem("focus.ss.user");
            }
        }
    }
}

function validateInputUsernameRegister(){
    let username = inputUsernameRegister.value; // username
    let validUsernameIcon = document.createElement("i")
    validUsernameIcon.classList.add("fa-solid", "fa-circle-check", "username");

    if(validateUsername(username)){ // validate username
        if(!document.querySelector("i.username")){
            spanUsernameRegister.prepend(validUsernameIcon);
        }
        spanUsernameRegister.classList.remove("text-error");
        spanUsernameRegister.classList.add("text-success");
        iconUsernameRegister.remove();
        inputUsernameRegister.classList.remove("border-error", "placeholder-red-400");
        inputUsernameRegister.classList.add("ring-gray-300");
    }else{
        spanUsernameRegister.classList.add("text-error");
        spanUsernameRegister.classList.remove("text-success");
        if(document.querySelector("i.username")){
            document.querySelector("i.username").remove();
        }
        spanUsernameRegister.prepend(iconUsernameRegister);
        inputUsernameRegister.classList.add("border-error", "placeholder-red-400");
        inputUsernameRegister.classList.remove("ring-gray-300");
    }
}

function validateEmail(string){
    let bool;
    let position_at = string.indexOf('@');
    let before_at = string.substring(0, position_at);
    let after_at = string.substring(position_at + 1, string.length);
    if(position_at > 0){
        if(before_at.charAt(0) != '.' && before_at.indexOf('.') != before_at.length - 1 &&
            after_at.charAt(0) != '.' && after_at.indexOf('.') != after_at.length - 1 && after_at.indexOf('.') > 0){
            bool = true;
        }else{
            bool = false;
        }
    }else{
        bool = false;
    }
    return bool;
}

function validateInputEmailRegister(){
    let email = inputEmailRegister.value; // email
    let validEmailIcon = document.createElement("i")
    validEmailIcon.classList.add("fa-solid", "fa-circle-check", "email", "pr-2");

    if(validateEmail(email)){ // validate email
        if(!document.querySelector("i.email")){
            spanEmailRegister.prepend(validEmailIcon);
        }
        spanEmailRegister.classList.remove("text-error");
        spanEmailRegister.classList.add("text-success");
        iconEmailRegister.remove();
        inputEmailRegister.classList.remove("border-error", "placeholder-red-400");
        inputEmailRegister.classList.add("ring-gray-300");
    }else{
        spanEmailRegister.classList.add("text-error");
        spanEmailRegister.classList.remove("text-success");
        if(document.querySelector("i.email")){
            document.querySelector("i.email").remove();
        }
        spanEmailRegister.prepend(iconEmailRegister);
        inputEmailRegister.classList.add("border-error", "placeholder-red-400");
        inputEmailRegister.classList.remove("ring-gray-300");
    }
}

function validatePasswordsRegister(string_1, string_2){
    return ((string_1.length > 5) && (string_1 === string_2));
}

function validateInputPasswordRegister(){
    let password1 = inputPassword1Register.value; // password
    let password2 = inputPassword2Register.value; // confirm password
    let validPasswordIcon = document.createElement("i")
    validPasswordIcon.classList.add("fa-solid", "fa-circle-check", "password", "pr-2");

    if(validatePasswordsRegister(password1, password2)){ // validate passwords // still yet to validate if it is already existent
        if(!document.querySelector("i.password")){
            spanPasswordRegister.prepend(validPasswordIcon);
        }
        spanPasswordRegister.classList.remove("text-error");
        spanPasswordRegister.classList.add("text-success");
        iconPasswordRegister.remove();
        inputPassword1Register.classList.remove("border-error", "placeholder-red-400");
        inputPassword1Register.classList.add("ring-gray-300");
        inputPassword2Register.classList.remove("border-error", "placeholder-red-400");
        inputPassword2Register.classList.add("ring-gray-300");
    }else{
        spanPasswordRegister.classList.add("text-error");
        spanPasswordRegister.classList.remove("text-success");
        if(document.querySelector("i.password")){
            document.querySelector("i.password").remove();
        }
        spanPasswordRegister.prepend(iconPasswordRegister);
        inputPassword1Register.classList.add("border-error", "placeholder-red-400");
        inputPassword1Register.classList.remove("ring-gray-300");
        inputPassword2Register.classList.add("border-error", "placeholder-red-400");
        inputPassword2Register.classList.remove("ring-gray-300");
    }

}


function openRegisterModal(){ // open the register modal
    registerModal.style.display = "block";
}

function openForgetPasswordModal(){ // open the forget password modal
    forgetPasswordModal.style.display = "block";
}

function closeRegisterModal(){ // close the register modal
    registerModal.style.display = "none";
}

function closeForgetPasswordModal(){ // close the forget password modal
    forgetPasswordModal.style.display = "none";
}

function disableRegisterBtn(){
    if(validateUsername(inputUsernameRegister.value) && 
    validatePasswordsRegister(inputPassword1Register.value, inputPassword2Register.value) && 
    validateEmail(inputEmailRegister.value)){
        registerBtn.disabled = false;
    }else{
        registerBtn.disabled = true;
    }
}

function rememberMe(){
    let bool = rememberMeCheckBox.checked;
    if(bool){
        sessionStorage.setItem("focus.ss.remember", true);
    }else{
        if(sessionStorage.getItem("focus.ss.remember")){
            sessionStorage.removeItem("focus.ss.remember");
            if(sessionStorage.getItem("focus.ss.user")){
                sessionStorage.removeItem("focus.ss.user");
            }
        }
        
    }
    
    return bool;
}

window.onload = function(){
    startLocalStorage();
    if(sessionStorage.getItem("focus.ss.remember") != null){
        if(sessionStorage.getItem("focus.ss.user")){
            var remember = JSON.parse(sessionStorage.getItem("focus.ss.user"));
        }
        inputUsername.value = remember.username;
        inputPassword.value = remember.password;
        submitLoginBtn.disabled = false;
    }else{
        submitLoginBtn.disabled = true;
    }
    
    registerBtn.disabled = true;
    submitForgetPassword.disabled = true;
}


// begin login calls

submitLoginBtn.addEventListener("click", rememberMe);
submitLoginBtn.addEventListener("click", validateSubmitLogin); // validate username and password onsubmit
inputUsername.addEventListener("change", validateUsernameAndPassword); // validate username onchange
inputPassword.addEventListener("change", validateUsernameAndPassword); // validate password onchange

// end login calls

// begin register calls

inputUsernameRegister.addEventListener("change", validateInputUsernameRegister);
inputPassword1Register.addEventListener("change", validateInputPasswordRegister);
inputPassword2Register.addEventListener("change", validateInputPasswordRegister);
inputEmailRegister.addEventListener("change", validateInputEmailRegister);

inputUsernameRegister.addEventListener("change", disableRegisterBtn);
inputPassword1Register.addEventListener("change", disableRegisterBtn);
inputPassword2Register.addEventListener("change", disableRegisterBtn);
inputEmailRegister.addEventListener("change", disableRegisterBtn);

registerNav.addEventListener("click", openRegisterModal);
registerLink.addEventListener("click", openRegisterModal); // open the register modal onclick
closeRegisterModalBtn.addEventListener("click", closeRegisterModal); // open the register modal onclick
registerBtn.addEventListener("click", prepareNewUser); // create new user and add it to localStorage onclick

// end register calls

// begin forget password calls

forgetPasswordLink.addEventListener("click", openForgetPasswordModal);
closeForgetPasswordModalSpan.addEventListener("click", closeForgetPasswordModal);
submitForgetPassword.addEventListener("click", changePassword);

forgetPasswordEmailInput.addEventListener("change", validateInputEmailForgetPassword);
forgetPasswordPassword1Input.addEventListener("change", validateInputPassword1ForgetPassword);
forgetPasswordPassword2Input.addEventListener("change", validateInputPassword2ForgetPassword);

forgetPasswordEmailInput.addEventListener("change", validateSubmitForgetPassword);
forgetPasswordPassword1Input.addEventListener("change", validateSubmitForgetPassword);
forgetPasswordPassword2Input.addEventListener("change", validateSubmitForgetPassword);

// end forget password calls
