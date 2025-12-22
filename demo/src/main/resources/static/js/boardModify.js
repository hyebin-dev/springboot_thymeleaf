console.log("boardModify.js in");

document.getElementById('modBtn').addEventListener('click',()=>{
    // title, content readonly 해지
    document.getElementById('t').readOnly = false;
    document.getElementById('c').readOnly = false;

    // modify, delete 버튼 삭제
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();

    // update할 버튼을 regBtn 이름으로 새로 생성
    // <button type="submit" id="regBtn" class="btn btn-warning">update</button>

    let regBtn = document.createElement('button'); // <button></button>
    // regBtn 속성 추가
    regBtn.setAttribute('type','submit'); // <button type="submit"></button>
    regBtn.setAttribute('id', 'regBtn');
    regBtn.classList.add('btn', 'btn-warning');
    regBtn.innerText = 'update';

    // form 태그의 자식으로 추가 (마지막에 추가)
    document.getElementById('modForm').appendChild(regBtn);
    

})