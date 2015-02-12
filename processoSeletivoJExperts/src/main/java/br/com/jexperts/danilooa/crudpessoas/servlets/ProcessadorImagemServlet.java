package br.com.jexperts.danilooa.crudpessoas.servlets;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jexperts.danilooa.crudpessoas.service.PessoaService;

@WebServlet("/ProcessadorImagemServlet")
public class ProcessadorImagemServlet extends HttpServlet {

    @Inject
    private PessoaService pessoaService;

    private static final long serialVersionUID = 1L;

    public ProcessadorImagemServlet() {
	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	byte[] imagem = (byte[]) request.getSession().getAttribute("imagem");

	if (imagem != null) {
	    escreverImagemNaOutput(response, imagem);
	    request.getSession().removeAttribute("imagem");
	    return;
	}

	String stringIdPessoa = request.getParameter("idPessoa");

	if (!stringIdPessoa.trim().isEmpty()) {
	    imagem = pessoaService.getImagemPessoa(Long.valueOf(stringIdPessoa));
	}

	if (imagem != null) {
	    escreverImagemNaOutput(response, imagem);
	    return;
	}

	response.sendRedirect("imagens/imagemNaoCadastrada.jpg");
    }

    private void escreverImagemNaOutput(HttpServletResponse response, byte[] imagem) throws IOException {
	response.setHeader("Content-Type", "image/jpeg");
	response.setHeader("Content-Length", "" + imagem.length);
	response.setHeader("Content-Disposition", "inline; filename=\"imagem.jpg\"");
	DataOutputStream dataOutputStream = new DataOutputStream(response.getOutputStream());
	dataOutputStream.write(imagem, 0, imagem.length);
	dataOutputStream.flush();
	dataOutputStream.close();
    }

}
