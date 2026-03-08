$(function () {
  // Adiciona o header da API globalmente (se ainda precisar)
  $.ajaxSetup({
    beforeSend: function(xhr) {
      xhr.setRequestHeader('X-API-KEY', 'I_Love_Milfs');
    }
  });

  function toast(msg) {
    const t = $('#toast');
    t.text(msg).fadeIn(300);
    setTimeout(() => t.fadeOut(400), 2500);
  }

  // Máscara de data __/__/____
  $(document).on('input', '#anoLancamento, .date-display', function () {
    let v = this.value.replace(/\D/g, '').substring(0,8);
    if (v.length >= 5) v = v.substring(0,2) + '/' + v.substring(2,4) + '/' + v.substring(4);
    else if (v.length >= 3) v = v.substring(0,2) + '/' + v.substring(2);
    this.value = v;
  });

  function formatDate(iso) {
    if (!iso) return '';
    return iso.substring(8,10) + '/' + iso.substring(5,7) + '/' + iso.substring(0,4);
  }

  function toISO(ddmmyyyy) {
    if (!ddmmyyyy || ddmmyyyy.length !== 10) return null;
    const [d, m, y] = ddmmyyyy.split('/');
    return `${y}-${m.padStart(2,'0')}-${d.padStart(2,'0')}`;
  }

  function loadFilmes() {
    $.get('/api/filmes')
      .done(filmes => {
        if (location.pathname.endsWith('cadastro')) {
          $('#listaFilmes').empty().append(filmes.map(f => `
            <div class="item" data-id="${f.id}">
              <div>
                <div style="font-weight:bold;font-size:18px">${f.titulo}</div>
                <small>${f.genero} • ${f.anoLancamento.substring(0,10)}</small>
                <div class="sinopse">${f.sinopse.replace(/\n/g,'<br>')}</div>
              </div>
              <button class="btn-ghost btnRemoverFilme">Remover</button>
            </div>
          `).join(''));
        }

        if (location.pathname.endsWith('analise')) {
          $('#listaAnalises').empty().append(filmes.map(f => `
            <div class="card" data-id="${f.id}">
              <div class="title">${f.titulo}</div>
              <div class="info">${f.genero} • ${f.anoLancamento.substring(0,10)}</div>
              <div class="sinopse">${f.sinopse.replace(/\n/g,'<br>')}</div>
              <form class="formAnalise">
                <div class="star-rating">
                  <span class="star" data-value="1">☆</span>
                  <span class="star" data-value="2">☆</span>
                  <span class="star" data-value="3">☆</span>
                  <span class="star" data-value="4">☆</span>
                  <span class="star" data-value="5">☆</span>
                </div>
                <input type="hidden" name="nota" value="0">
                <textarea name="texto" placeholder="Sua análise..." required></textarea>
                <button class="btn" type="submit">Salvar Análise</button>
              </form>
              <div class="analises"></div>
            </div>
          `).join(''));
          filmes.forEach(f => loadAnalises(f.id));
        }

        if (location.pathname.endsWith('gerenciamento')) {
          $('#listaGerenciamento').empty().append(filmes.map(f => `
            <div class="card" data-id="${f.id}">
              <div class="title">${f.titulo}</div>
              <div class="fields">
                <input name="titulo" value="${f.titulo}">
                <input name="genero" value="${f.genero}">
                <input class="date-display" value="${formatDate(f.anoLancamento)}">
                <textarea name="sinopse">${f.sinopse}</textarea>
              </div>
              <button class="btn btnAtualizar">Atualizar</button>
            </div>
          `).join(''));
        }
      })
      .fail(() => toast('Erro ao conectar com o servidor'));
  }

  function loadAnalises(filmeId) {
    $.get(`/api/filmes/${filmeId}/analises`)
      .done(analises => {
        const container = $(`[data-id="${filmeId}"] .analises`).empty();
        analises.forEach(a => {
          container.append(`
            <div class="analise-item" data-id="${a.id}">
              <div class="analise-meta">
                <div>${'★'.repeat(a.nota)}${'☆'.repeat(5 - a.nota)} (${a.nota}/5)</div>
                <div style="flex:1"></div>
                <button class="btn-ghost btnEditarAnalise">Editar</button>
                <button class="btn-ghost btnRemoverAnalise">Remover</button>
              </div>
              <textarea class="analise-text" readonly>${a.texto.replace(/\n/g, '<br>')}</textarea>
              <div class="edit-controls" style="display:none;margin-top:12px">
                <div class="star-rating">
                  <span class="star" data-value="1">☆</span>
                  <span class="star" data-value="2">☆</span>
                  <span class="star" data-value="3">☆</span>
                  <span class="star" data-value="4">☆</span>
                  <span class="star" data-value="5">☆</span>
                </div>
                <button class="btn btnSalvarEdicao">Salvar</button>
                <button class="btn-ghost btnCancelarEdicao">Cancelar</button>
              </div>
            </div>
          `);

          // Preenche as estrelas da edição com a nota atual
          const editRating = container.find(`[data-id="${a.id}"] .edit-controls .star-rating`);
          editRating.find('.star').slice(0, a.nota).removeClass('☆').addClass('★').addClass('filled');
        });
      })
      .fail(() => toast('Erro ao carregar análises'));
  }

  // Cadastro de filme
  $('#formFilme').on('submit', function(e) {
    e.preventDefault();
    const iso = toISO($('#anoLancamento').val());
    if (!iso) return toast('Data inválida (dd/mm/yyyy)');
    const filme = {
      titulo: $('[name=titulo]').val().trim(),
      genero: $('[name=genero]').val().trim(),
      anoLancamento: iso,
      sinopse: $('[name=sinopse]').val().trim()
    };
    $.ajax({
      url:'/api/filmes',
      type:'POST',
      contentType:'application/json',
      data:JSON.stringify(filme)
    })
      .done(() => { toast('Cadastrado!'); this.reset(); loadFilmes(); })
      .fail(() => toast('Erro no cadastro'));
  });

  // Remover filme
  $(document).on('click', '.btnRemoverFilme', function() {
    if(confirm('Remover este filme?')) {
      $.ajax({url:'/api/filmes/'+$(this).closest('[data-id]').data('id'), type:'DELETE'})
        .done(() => { toast('Removido'); loadFilmes(); })
        .fail(() => toast('Erro ao remover'));
    }
  });

  // Atualizar filme
  $(document).on('click', '.btnAtualizar', function() {
    const card = $(this).closest('[data-id]');
    const iso = toISO(card.find('.date-display').val());
    if(!iso) return toast('Data inválida');
    const filme = {
      titulo: card.find('[name=titulo]').val().trim(),
      genero: card.find('[name=genero]').val().trim(),
      anoLancamento: iso,
      sinopse: card.find('[name=sinopse]').val().trim()
    };
    $.ajax({
      url:'/api/filmes/'+card.data('id'),
      type:'PUT',
      contentType:'application/json',
      data:JSON.stringify(filme)
    })
      .done(() => { toast('Atualizado'); loadFilmes(); })
      .fail(() => toast('Erro ao atualizar'));
  });

  // Clique nas estrelas - usa símbolo vazio (☆) e preenche com ★
  $(document).on('click', '.star', function() {
    const val = parseInt($(this).data('value'));
    const ratingContainer = $(this).closest('.star-rating');
    ratingContainer.find('.star')
      .removeClass('filled')
      .text('☆')
      .slice(0, val)
      .addClass('filled')
      .text('★');
  });

  // Nova análise - permite 0 estrelas
  $(document).on('submit', '.formAnalise', function(e) {
    e.preventDefault();
    const filmeId = $(this).closest('[data-id]').data('id');
    const filledStars = $(this).find('.star-rating .star.filled').length;
    const nota = filledStars;  // pega diretamente das estrelas clicadas
    const texto = $(this).find('[name=texto]').val().trim();

    if (!texto) return toast('Escreva o texto da análise');

    $.ajax({
      url:`/api/filmes/${filmeId}/analises`,
      type:'POST',
      contentType:'application/json',
      data:JSON.stringify({nota, texto})
    })
      .done(() => {
        toast('Análise salva');
        this.reset();
        $(this).find('.star-rating .star').removeClass('filled').text('☆');
        loadAnalises(filmeId);
      })
      .fail(() => toast('Erro ao salvar análise'));
  });

  // Abrir edição
  $(document).on('click', '.btnEditarAnalise', function() {
    const item = $(this).closest('.analise-item');
    item.find('.edit-controls').show();
    item.find('.analise-text').prop('readonly', false).focus();

    const currentNotaText = item.find('.analise-meta div:first').text();
    const currentNota = parseInt(currentNotaText.match(/\((\d+)\/5\)/)[1]);
    const editStars = item.find('.edit-controls .star-rating .star');
    editStars.removeClass('filled').text('☆');
    editStars.slice(0, currentNota).addClass('filled').text('★');
  });

  // Cancelar edição
  $(document).on('click', '.btnCancelarEdicao', function() {
    const item = $(this).closest('.analise-item');
    item.find('.edit-controls').hide();
    item.find('.analise-text').prop('readonly', true);
  });

  // Salvar edição
  $(document).on('click', '.btnSalvarEdicao', function() {
    const item = $(this).closest('.analise-item');
    const id = item.data('id');
    const nota = item.find('.edit-controls .star.filled').length;
    const texto = item.find('.analise-text').val().trim();

    if (!texto) return toast('Texto não pode ficar vazio');

    $.ajax({
      url:`/api/filmes/analises/${id}`,
      type:'PUT',
      contentType:'application/json',
      data:JSON.stringify({nota, texto})
    })
      .done(() => {
        toast('Análise atualizada');
        item.find('.edit-controls').hide();
        item.find('.analise-text').prop('readonly', true);
        item.find('.analise-meta div:first').html(`${'★'.repeat(nota)}${'☆'.repeat(5 - nota)} (${nota}/5)`);
        loadAnalises(item.closest('[data-id]').data('id'));
      })
      .fail(() => toast('Erro ao atualizar'));
  });

  // Remover análise
  $(document).on('click', '.btnRemoverAnalise', function() {
    if (!confirm('Remover esta análise?')) return;

    const item = $(this).closest('.analise-item');
    const id = item.data('id');
    const filmeId = item.closest('[data-id]').data('id');

    $.ajax({
      url: `/api/filmes/analises/${id}`,
      type: 'DELETE'
    })
    .done(() => {
      toast('Análise removida');
      item.fadeOut(300, function() { $(this).remove(); });
    })
    .fail(() => toast('Erro ao remover análise'));
  });

  // Inicialização
  if (location.pathname.match(/cadastro|analise|gerenciamento/)) {
    loadFilmes();
  }
});