//profile
let fields = document.getElementsByClassName("form-control");
let editProfileBtn = document.getElementById("edit-profile-btn");
let submitChangesBtn = document.getElementById("submit-changes-btn");

for(let i = 0; i < fields.length; ++i) {
    fields[i].addEventListener("input", () => {
        submitChangesBtn.disabled = false;
    })
}

editProfileBtn.addEventListener("click", () => {
    for (let i = 0; i < fields.length; i++) {

        if (fields[i].readOnly === true && fields[i].name !== "") {
            fields[i].readOnly = false;
            fields[i].setAttribute('required', 'true')
        }
    }
})

