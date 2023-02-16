let addBtn = document.getElementById('addTest')
let deleteBtn = document.getElementById('deleteTest')

let maxAmountOfQuestions = document.getElementById('maxAmountOfAnswers').textContent;
let placeholderValue = document.getElementById('placeholderValue').textContent;
let validationText = document.getElementById('validationText').textContent
let form = document.getElementById('answersDiv')
let count = 1

addBtn.addEventListener('click',function(e) {
    count++
    let html = `<div class="form-check" id="formCheckDiv${count}">
            <input class="form-check-input" name="answer${count}correct" type="checkbox">
            <input type="text" class="form-control" name="answer${count}" required
            placeholder="${placeholderValue}"
            oninvalid="this.setCustomValidity('${validationText}')"
            oninput="this.setCustomValidity('')">
        </div>`

    deleteBtn.style.display = 'inline-block';
    if(count == maxAmountOfQuestions){
        addBtn.style.display = 'none';
    }
    form.innerHTML += html
})

deleteBtn.addEventListener('click',function(e) {
    addBtn.style.display = 'inline-block';

    if(count > 1) {
        document.querySelector(`#formCheckDiv${count}`).remove();
        count--;
    }

    if(count <= 1){
        deleteBtn.style.display = 'none';
    }

    console.log(count)
})
