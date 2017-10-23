package org.molgenis.app.controller;

public class Effect
{
	private final String key;
	String alt_Allele;
	String gene_Name;
	String annotation;
	String putative_impact;
	String gene_ID;
	String feature_type;
	String feature_ID;
	String transcript_biotype;
	String rank_total;
	String hGVS_c;
	String hGVS_p;
	String cDNA_position;
	String cDS_position;
	String protein_position;
	String distance_to_feature;
	String errors;
	String classification;
	String confidence;
	String reason;

	public Effect(String key, String effect)
	{
		String[] effectArray = effect.split("\\|");
		this.key = key;
		this.alt_Allele = effectArray[0];
		this.gene_Name = effectArray[1];
		this.annotation = effectArray[2];
		this.putative_impact = effectArray[3];
		this.gene_ID = effectArray[4];
		this.feature_type = effectArray[5];
		this.feature_ID = effectArray[6];
		this.transcript_biotype = effectArray[7];
		this.rank_total = effectArray[8];
		this.hGVS_c = effectArray[9];
		this.hGVS_p = effectArray[10];
		this.cDNA_position = effectArray[11];
		this.cDS_position = effectArray[12];
		this.protein_position = effectArray[13];
		this.distance_to_feature = effectArray[14];
		this.errors = effectArray[15];
		this.classification = effectArray[16];
		this.confidence = effectArray[17];
		this.reason = effectArray[18];
	}

	public String getAlt_Allele()
	{
		return alt_Allele;
	}

	public void setAlt_Allele(String alt_Allele)
	{
		this.alt_Allele = alt_Allele;
	}

	public String getGene_Name()
	{
		return gene_Name;
	}

	public void setGene_Name(String gene_Name)
	{
		this.gene_Name = gene_Name;
	}

	public String getAnnotation()
	{
		return annotation;
	}

	public void setAnnotation(String annotation)
	{
		this.annotation = annotation;
	}

	public String getPutative_impact()
	{
		return putative_impact;
	}

	public void setPutative_impact(String putative_impact)
	{
		this.putative_impact = putative_impact;
	}

	public String getGene_ID()
	{
		return gene_ID;
	}

	public void setGene_ID(String gene_ID)
	{
		this.gene_ID = gene_ID;
	}

	public String getFeature_type()
	{
		return feature_type;
	}

	public void setFeature_type(String feature_type)
	{
		this.feature_type = feature_type;
	}

	public String getFeature_ID()
	{
		return feature_ID;
	}

	public void setFeature_ID(String feature_ID)
	{
		this.feature_ID = feature_ID;
	}

	public String getTranscript_biotype()
	{
		return transcript_biotype;
	}

	public void setTranscript_biotype(String transcript_biotype)
	{
		this.transcript_biotype = transcript_biotype;
	}

	public String getRank_total()
	{
		return rank_total;
	}

	public void setRank_total(String rank_total)
	{
		this.rank_total = rank_total;
	}

	public String gethGVS_c()
	{
		return hGVS_c;
	}

	public void sethGVS_c(String hGVS_c)
	{
		this.hGVS_c = hGVS_c;
	}

	public String gethGVS_p()
	{
		return hGVS_p;
	}

	public void sethGVS_p(String hGVS_p)
	{
		this.hGVS_p = hGVS_p;
	}

	public String getcDNA_position()
	{
		return cDNA_position;
	}

	public void setcDNA_position(String cDNA_position)
	{
		this.cDNA_position = cDNA_position;
	}

	public String getcDS_position()
	{
		return cDS_position;
	}

	public void setcDS_position(String cDS_position)
	{
		this.cDS_position = cDS_position;
	}

	public String getProtein_position()
	{
		return protein_position;
	}

	public void setProtein_position(String protein_position)
	{
		this.protein_position = protein_position;
	}

	public String getDistance_to_feature()
	{
		return distance_to_feature;
	}

	public void setDistance_to_feature(String distance_to_feature)
	{
		this.distance_to_feature = distance_to_feature;
	}

	public String getErrors()
	{
		return errors;
	}

	public void setErrors(String errors)
	{
		this.errors = errors;
	}

	public String getClassification()
	{
		return classification;
	}

	public void setClassification(String classification)
	{
		this.classification = classification;
	}

	public String getConfidence()
	{
		return confidence;
	}

	public void setConfidence(String confidence)
	{
		this.confidence = confidence;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}
}
