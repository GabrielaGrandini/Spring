document.getElementById('form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const perfil = document.getElementById('perfil').value;
    const endereco = document.getElementById('endereco').value;
    const bairro = document.getElementById('bairro').value;
    const complemento = document.getElementById('complemento').value;
    const cep = document.getElementById('cep').value;
    const cidade = document.getElementById('cidade').value;
    const estado = document.getElementById('estado').value;


    const usuario = {
        nome: nome,
        email: email,
        senha:senha,
        perfil: perfil,
        endereco: endereco,
        bairro: bairro,
        complemento: complemento,
        cep: cep,
        cidade: cidade,
        estado: estado
    };

    try {
        const response = await fetch('http://localhost:8080/usuarios', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }, 
             body: JSON.stringify(usuario)
            // body: new URLSearchParams ({
            //     nome, 
            //     email,
            //     senha, 
            //     perfil,
            //     endereco,
            //     bairro, 
            //     complemento,
            //     cep,
            //     cidade,
            //     estado
            // })
        })    
        .then(res => {
        if (!res.ok) throw new Error();
        return res.json();
        })
        .then(data => {
        localStorage.setItem('token', data.token);
        //window.location.href = 'usuarios.html';
       document.getElementById('resultado').textContent = "Usuário cadastrado com sucesso!"
    })
    .catch(() => {
        mensagem.innerHTML = '<span style="color:red">Login inválido</span>';
    });

        // if(!response.okay) {
        //     throw new Error ('Erro na requisição');
        // }

        // const data = await response.json ();

        // if(data.erro){
        //     document.getElementById('erro').textContent = data.erro;
        // } else {
        //     document.getElementById('resultado').textContent = data.resultado;
            
        // }
        

    } catch {
        document.getElementById('erro').textContent = ' ';
    }
})