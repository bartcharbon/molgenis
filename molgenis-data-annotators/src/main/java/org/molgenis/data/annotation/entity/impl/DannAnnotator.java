package org.molgenis.data.annotation.entity.impl;

import static org.molgenis.MolgenisFieldTypes.FieldTypeEnum.DECIMAL;
import static org.molgenis.data.annotator.websettings.DannAnnotatorSettings.Meta.DANN_LOCATION;
import static org.molgenis.data.meta.EntityMetaData.AttributeRole.ROLE_ID;
import static org.molgenis.data.vcf.VcfRepository.ALT_META;
import static org.molgenis.data.vcf.VcfRepository.CHROM_META;
import static org.molgenis.data.vcf.VcfRepository.POS_META;
import static org.molgenis.data.vcf.VcfRepository.REF_META;

import java.util.ArrayList;
import java.util.List;

import org.molgenis.data.DataService;
import org.molgenis.data.Entity;
import org.molgenis.data.annotation.RepositoryAnnotator;
import org.molgenis.data.annotation.entity.AnnotatorInfo;
import org.molgenis.data.annotation.entity.AnnotatorInfo.Status;
import org.molgenis.data.annotation.entity.EntityAnnotator;
import org.molgenis.data.annotation.filter.MultiAllelicResultFilter;
import org.molgenis.data.annotation.impl.cmdlineannotatorsettingsconfigurer.SingleFileLocationCmdLineAnnotatorSettingsConfigurer;
import org.molgenis.data.annotation.query.LocusQueryCreator;
import org.molgenis.data.annotation.resources.Resource;
import org.molgenis.data.annotation.resources.Resources;
import org.molgenis.data.annotation.resources.impl.ResourceImpl;
import org.molgenis.data.annotation.resources.impl.SingleResourceConfig;
import org.molgenis.data.annotation.resources.impl.TabixRepositoryFactory;
import org.molgenis.data.meta.AttributeMetaData;
import org.molgenis.data.meta.EntityMetaDataImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DannAnnotator
{
	public static final String NAME = "dann";

	public static final String DANN_SCORE = "DANN_SCORE";
	public static final String DANN_SCORE_LABEL = "DANNSCORE";
	public static final String DANN_TABIX_RESOURCE = "DANNTabixResource";

	@Autowired
	private Entity dannAnnotatorSettings;

	@Autowired
	private DataService dataService;

	@Autowired
	private Resources resources;

	@Bean
	public RepositoryAnnotator dann()
	{
//		List<AttributeMetaData> attributes = new ArrayList<>();
//		AttributeMetaData dann_score = new AttributeMetaData(DANN_SCORE, DECIMAL)
//				.setDescription("deleterious score of genetic variants using neural networks.")
//				.setLabel(DANN_SCORE_LABEL);
//
//		attributes.add(dann_score);
//
//		AnnotatorInfo dannInfo = AnnotatorInfo.create(Status.READY, AnnotatorInfo.Type.PATHOGENICITY_ESTIMATE, NAME,
//				"Annotating genetic variants, especially non-coding variants, "
//						+ "for the purpose of identifying pathogenic variants remains a challenge."
//						+ " Combined annotation-dependent depletion (CADD) is an al- gorithm designed "
//						+ "to annotate both coding and non-coding variants, and has been shown to outper- form "
//						+ "other annotation algorithms. CADD trains a linear kernel support vector machine (SVM) "
//						+ "to dif- ferentiate evolutionarily derived, likely benign, alleles from simulated, "
//						+ "likely deleterious, variants. However, SVMs cannot capture non-linear relationships"
//						+ " among the features, which can limit performance. To address this issue, we have"
//						+ " developed DANN. DANN uses the same feature set and training data as CADD to train"
//						+ " a deep neural network (DNN). DNNs can capture non-linear relation- ships among "
//						+ "features and are better suited than SVMs for problems with a large number of samples "
//						+ "and features. We exploit Compute Unified Device Architecture-compatible "
//						+ "graphics processing units and deep learning techniques such as dropout and momentum "
//						+ "training to accelerate the DNN training. DANN achieves about a 19%relative reduction "
//						+ "in the error rate and about a 14%relative increase in the area under the curve (AUC) metric "
//						+ "over CADD’s SVM methodology. "
//						+ "All data and source code are available at https://cbcl.ics.uci.edu/ public_data/DANN/.",
//				attributes);
//
		EntityAnnotator entityAnnotator = null; // FIXME new AnnotatorImpl(DANN_TABIX_RESOURCE, dannInfo, new LocusQueryCreator(),
//				new MultiAllelicResultFilter(attributes), dataService, resources,
//				new SingleFileLocationCmdLineAnnotatorSettingsConfigurer(DANN_LOCATION, dannAnnotatorSettings));

		return new RepositoryAnnotatorImpl(entityAnnotator);
	}

	@Bean
	Resource dannResource()
	{
		Resource dannTabixResource = null;
// FIXME
//		String idAttrName = "id";
//		EntityMetaDataImpl repoMetaData = new EntityMetaDataImpl(DANN_TABIX_RESOURCE);
//		repoMetaData.addAttribute(CHROM_META);
//		repoMetaData.addAttribute(POS_META);
//		repoMetaData.addAttribute(REF_META);
//		repoMetaData.addAttribute(ALT_META);
//		repoMetaData.addAttribute(new AttributeMetaData("DANN_SCORE", DECIMAL));
//		repoMetaData.addAttribute(idAttrName, ROLE_ID).setVisible(false);
//
//		dannTabixResource = new ResourceImpl(DANN_TABIX_RESOURCE,
//				new SingleResourceConfig(DANN_LOCATION, dannAnnotatorSettings),
//				new TabixRepositoryFactory(repoMetaData));

		return dannTabixResource;
	}
}
