package org.molgenis.app;

public class Author
{
	String firstName;
	String lastName;
	String initials;

	public Author(String firstName, String lastName, String initials)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.initials = initials;
	}

	@Override
	public String toString()
	{
		return lastName + "," + initials;
	}
}
