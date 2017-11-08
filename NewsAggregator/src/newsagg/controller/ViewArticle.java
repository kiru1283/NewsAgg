package newsagg.controller;

import newsagg.exceptions.ArticleException;

/**
 * Class to open Article in the default web browser
 * 
 * @author Kiruthiga
 *
 */
public class ViewArticle {

	private String url;

	/**
	 * Constructor method used to set the url to be opened
	 * 
	 * @param link
	 *            - url of article
	 */
	public ViewArticle(String link) {
		this.url = link;
	}

	/**
	 * Method to use System Runtime to open the article URL with the default browser
	 * 
	 * @throws ArticleException
	 *             - when error occurs while opening url
	 */
	public boolean openArticle() throws ArticleException {
		/*
		 * for windows OS try { Desktop desktop = java.awt.Desktop.getDesktop(); //URI
		 * oURL = new URI("http://www.google.com"); URI oURL = new URI(url);
		 * desktop.browse(oURL); } catch (Exception e) { e.printStackTrace(); }
		 */

		// code that can be used on all Operating Systems
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();

		boolean retVal = false;

		try {

			if (os.indexOf("win") >= 0) {

				// this doesn't support showing urls in the form of "page.html#nameLink"
				rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
				
				retVal = true;

			} else if (os.indexOf("mac") >= 0) {

				rt.exec("open " + url);
				retVal = true;

			} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

				// Do a best guess on unix until we get a platform independent way
				// Build a list of browsers to try, in this order.
				String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links",
						"lynx" };

				// Build a command string which looks like "browser1 "url" || browser2 "url"
				// ||..."
				StringBuffer cmd = new StringBuffer();
				for (int i = 0; i < browsers.length; i++)
					cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");

				rt.exec(new String[] { "sh", "-c", cmd.toString() });
				retVal = true;

			} else {
				retVal = false;
			}
		} catch (Exception e) {
			throw new ArticleException("Error while opening article with url: " + url);

		}
		
		return retVal;

	}

}
