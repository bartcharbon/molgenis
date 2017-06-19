package org.molgenis.app;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Article
{
	String pubmedId;
	String abstractText;
	List<Author> authors;
	String articleTitle;
	String journalTitle;
	String year;

	public Article(String pubmedId, String abstractText, List<Author> authors, String articleTitle, String journalTitle,
			String year)
	{
		this.pubmedId = pubmedId;
		this.abstractText = abstractText.replace("\"", "\\\"");
		this.authors = authors;
		this.articleTitle = articleTitle;
		this.journalTitle = journalTitle;
		this.year = year;
	}

	@Override
	public String toString()
	{
		return "{" + "\"pubmedId\":\"" + pubmedId + '"' + ", \"abstractText\":\"" + abstractText + '"'
				+ ", \"authors\":\"" + StringUtils.join(authors, ";") + "\", \"articleTitle\":\"" + articleTitle + '"'
				+ ", \"journalTitle\":\"" + journalTitle + '"' + ", \"year\":\"" + year + '"' + '}';
	}
}
