<#-- modal header -->
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">BioSchema for ${entity.get("identifier")?html}</h4>
</div>

<#-- modal body -->
<div class="modal-body">
    <pre id="molgenis_bioschema">
    </pre>
</div>

<#-- modal footer -->
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
</div>
<script>
    $.get('/api/bioschema/${entity.get("identifier")}', function (data) {
        $('#molgenis_bioschema').text(data);
    });
</script>