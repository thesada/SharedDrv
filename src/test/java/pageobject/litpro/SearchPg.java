package pageobject.litpro;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import pageobject.ParentPage;
import pageobject.litpro.LitProHomePg.LitProUserType;
@SuppressWarnings("unused")
public class SearchPg extends ParentPage {
	WebDriver driver;
	private LitProUserType lpUserType;
	final String PAGE_TITLE = "Scholastic Literacy Pro";
	final String PAGE_HEADER = "Search";

	@FindBy(xpath = "//input[contains(@ng-model, 'searchString')]")
	private WebElement searchTextBoxForTeacher;

	@FindBy(xpath = "//input[@ng-model='searchKeyWord']")
	private WebElement searchTextBoxForStudent;

	@FindBy(xpath = "//button[text()='Search']")
	private WebElement searchButton;

	@FindBy(xpath = "//p[text()='Search']")
	private WebElement pgHeaderforStudent;

	@FindBy(xpath = "//h2[text()='Search']")
	private WebElement pgHeaderforTeacher;

	@FindBy(xpath = "//span[text()='Quizzes Only']")
	private WebElement quizzesOnlyChkBox;

	@FindBy(xpath = "//input[@ng-click='listViewButtonClick()']")
	private WebElement listViewButton;

	@FindBy(xpath = "//div[@id='quizList']")
	private WebElement searchResultsParentStudPg;

	@FindBy(xpath = "//table[@id='search-results']")
	private WebElement searchResultsParentTeachPg;

	public SearchPg(WebDriver driver, LitProUserType userType) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver,DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.waitForPageLoad(DRIVER_WAIT);
		this.lpUserType = userType;
	}

	 
	public String getExpectedTitle() {
		return PAGE_TITLE;
	}

	public String getPageHeader() {
		String header = "";
		if (lpUserType == LitProUserType.TEACHER) {
			header= this.getText(pgHeaderforTeacher);
		} else if (lpUserType == LitProUserType.STUDENT) {
			header= this.getText(pgHeaderforStudent);
		}
		this.reportLog("Search Page Header: " + header);
		return header;
	}

	public void typeKeyAndSearch(String key) {
		this.reportLog("Enter Search Key: '" +key + "' and hit Search button");
		
		if (lpUserType == LitProUserType.TEACHER) {
			this.type(searchTextBoxForTeacher, key);
			searchTextBoxForTeacher.submit();
		} else if (lpUserType == LitProUserType.STUDENT) {
			this.type(searchTextBoxForStudent, key);
			searchTextBoxForStudent.submit();
		}
	}

	public void checkQuizzesOnly() {
		this.reportLog("Select 'Quizzes Only' checkbox");
		this.click(quizzesOnlyChkBox);
	}

	public void switchToListView() {
		this.click(listViewButton);
	}

	public List<ResultBook> getSearchResultBooks() {
		if (lpUserType == LitProUserType.TEACHER) {
			return getSearchResultBooksForTeachers();
		} else if (lpUserType == LitProUserType.STUDENT) {
			return getSearchResultBooksForStudents();
		}

		return null;
	}

	public List<ResultBook> getSearchResultBooksForTeachers() {
		this.lazyWait(5);
		List<ResultBook> bookList = new ArrayList<ResultBook>();
		List<WebElement> children = this.getChildElements(searchResultsParentTeachPg, By.xpath("./tbody/tr"));
		this.reportLog((int)(children.size()/3) + " Books Displayed(Teacher Search)");
		if (children != null) {
			Iterator<WebElement> resultRows = children.iterator();
			while (resultRows.hasNext()) {
				WebElement firstTr = resultRows.next();
				WebElement secTr = resultRows.next();
				WebElement thirdTr = resultRows.next();

				ResultBook newBook = new ResultBook();
				// title
				WebElement bookTitleElement = this.getChildElement(firstTr,By.xpath(".//td[@data-title='Title']"));
				newBook.title = this.getText(bookTitleElement);

				// author
				WebElement bookAutElement = this.getChildElement(firstTr,By.xpath(".//td[@data-title='Author']"));
				newBook.author = this.getText(bookAutElement);

				// has quiz
				WebElement quizButtonElement = this.getChildElement(thirdTr, By.xpath(".//button[span[text()='View Quiz']]"));
				if (quizButtonElement != null) newBook.hasQuizButton = true;
				
				this.reportLog(newBook.toString());
				bookList.add(newBook);
			}
		}

		return bookList;
	}

	public List<ResultBook> getSearchResultBooksForStudents() {
		List<ResultBook> bookList = new ArrayList<ResultBook>();
		List<WebElement> children = this.getChildElements(searchResultsParentStudPg, By.xpath("./div[contains(@class, 'bookRecItem')]"));
		this.reportLog(children.size() + " Books Displayed(Student Search)");
		for (WebElement book : children) {
			ResultBook newBook = new ResultBook();
			// title
			WebElement bookTitleElement = this.getChildElement(book, By.xpath(".//p[contains(@class, 'title')]"));
			newBook.title = this.getText(bookTitleElement);
			// author
			newBook.author = "";
			// has quiz
			WebElement quizButtonElement = this.getChildElement(book, By.xpath(".//input[@value='Take the Quiz']"));
			if (quizButtonElement != null) newBook.hasQuizButton = true;
			this.reportLog(newBook.toString());
			bookList.add(newBook);
		}
		return bookList;
	}
	
	/*Randomly clicks the TakeQuiz button in book results and returns the book title*/
	public String clickRandomBookTakeQuizButton(){
		String bookTitle = "";
		int random;
		List<WebElement> children = this.getChildElements(searchResultsParentStudPg, By.xpath("./div[contains(@class, 'bookRecItem') and .//input[@value='Take the Quiz']]"));
		if(children.size()>0){
			
			random = (int)(Math.random() * children.size());
			WebElement bookElement = children.get(random);
			//get title
			WebElement bookTitleElement = this.getChildElement(bookElement, By.xpath(".//p[contains(@class, 'title')]"));
			bookTitle = this.getText(bookTitleElement);
			
			//click take quiz button
			WebElement quizBtnElement = this.getChildElement(bookElement, By.xpath(".//input[@value='Take the Quiz']"));
			if(quizBtnElement!=null){
				quizBtnElement.click();
			}
		}
		return bookTitle;
	}
	public class ResultBook {
		public String title = "";
		public String author = "";
		public boolean hasQuizButton = false;
		
		@Override
		 public String toString(){
			return "{BookTitle:"+title+", Author:"+ title +",Quiz Button:"+ hasQuizButton +"}";
		}
	}

}
