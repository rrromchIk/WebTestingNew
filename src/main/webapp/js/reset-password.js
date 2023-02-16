let newPassword = document.getElementById("newPassword");
let confirmPassword = document.getElementById("confirmPassword");
let resetButton = document.getElementById("resetButton");
let feedBack = document.getElementById("feedBack");


function check() {
    var newPasswordValue = newPassword.value;
    var confirmPasswordValue = confirmPassword.value;

    console.log(newPasswordValue)
    console.log(confirmPasswordValue)


    if (newPasswordValue != confirmPasswordValue) {
        console.log("passwords not equals");
        resetButton.disabled = true;
        feedBack.removeAttribute("hidden");
    } else {
        console.log("passwords equals")
        resetButton.disabled = false;
        feedBack.setAttribute("hidden","");
    }
}


newPassword.addEventListener("input", check);
confirmPassword.addEventListener("input", check);
