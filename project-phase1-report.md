# Project Phase 1 Report - Group 14

## Introduction

This report documents the work completed for Phase 1 of the CITS5501 Project.
The project involves developing a prototype terminal-based interface for Koto
Enterprises' "Tachi" travel reservation system, designed to improve efficiency
for advanced users to compared to the existing web interface.

Phase 1 deliverables focus on three key tasks. First, setting up the group
GitHub Repository, including documentation and correct use of pull requests.
Second, implementing and testing the `DateTimeChecker.java` class together with
JUnit test suite in `DateTimeCheckerTest.java`, ensuring proper validation of
dates and datetimes. Finally, the software quality plan assurance plan is
outlined, describing coding standards, testing practices and peer review
processes to maintain high-quality code.

This report documents the approach taken and decisions for each task, supported
by evidence for our GitHub repository.

## [1] Repository and File Creation

### [1.1] Repository Creation

The first task was to set up the repository and invite group members. A private
group repository was created called `CITS5501-Project-Group14`. See repository
link: `https://github.com/Jeremy-JR0/CITS5501-Project-Group14`

The necessary files were then added to main using a separate branch called
`project-files`. See pull request below:
`https://github.com/Jeremy-JR0/CITS5501-Project-Group14/pull/1`

### [1.2] Add README and Project Report Files

Another branch was made to create and add the necessary files: `README.md` and
`project-phase1-report.md`. This can be seen through the following pull
request:
`https://github.com/Jeremy-JR0/CITS5501-Project-Group14/pull/2`

### [1.3] File Update Branch

A branch was created to update the contents of the files, commit changes and
ensure starter checks run.

### [1.4] Finalised Review

## [2]

### [2.3] Software Quality Assurance Plan

The Software Quality Assurance (SQA) plan is designed to ensure that the code 
produced for the "Tachi" prototype is correct, maintainable, and developed
collaboratively using professional practices. The plan combines coding
standards, systematics testing, and GitHub workflows to deliver reliable
results.

1. **Coding Standards**
   Consistent Java coding conventions will be followed so that the code remains
   clear and easy to review. Variable and method names will be meaningful,
   indentation consistent, and each method (including JUnit tests) will
   contain Javadoc comments as per the guidelines. Code must compile without
   warnings/issues before it is merged. TO avoid poor practice will output to
   `System.out` or `System.err` unless explicitly required.

2. **Unit Testing with JUnit**
   Testing will focus on the `DateTimeChecker.java` class. Comprehensive JUnit
   test cases will be written in `DateTimeCheckerTest.java` to check both
   syntactic and semantic validity of dates and datetimes. Test cases will
   cover normal inputs as well as boundary conditions and invalid inputs. 
   The class will use Java’s built-in date libraries solely to obtain the 
   current date and time, while our team will develop the validation 
   process to ensure the implementation is thoroughly verified. Tests
   will be designed for repeatability so they can be rerun after every change.

3. **Pull Requests and Peer Review**
   All changes will be made in branches and merged through pull request. Each
   pull request will be reviewed (excluding initial setup of GitHub/repository)
   by one or more reviewers who are not the original author. Reviews will
   check that the code is correct, readable, adheres to standards, and passes
   necessary tests. GitHubs review system will maintain a clear history of
   comments, approvals, and changes, which are referenced in this report. This
   ensures accountability and collaborative quality assurance.

4. **Documenting and Reporting**
   Each pull request will include a short description linking the change to a
   task or issue. Any assumptions will be documented where necessary and
   referenced in code comments where relevant. This report includes links to
   example pull requests and reviews to demonstrate the practices used. This
   creates traceability from requirements to implementation and review.

In summary, given the small size of the codebase in Phase 1, the focus was put
on unit testing and code review rather than heavy practices. There were
approaches consider (discussed in the next section), but were excluded due to
setup effort outweighing benefit at this stage. The focus remains on test
coverage and collaborative review, which provides the highest impact for effort
invested. This balances thoroughness with efficiency, maintaining correctness
and reliability while meeting the project specification and fostering
professional teamwork.

## [3] Software Quality Assurance Rejected Idea

One Software Quality Assurance (SQA) activity considered was the use of Formal
Technical Reviews (FTRs). FTRs are structured, meeting-based reviews where team
members and stakeholders walk through code, designs, or documents to detect
defects early in development. They are widely recognised as an effective method
for preventing errors from progressing further into the project lifecycle and
for encouraging shared learning within a team. As they combine defect detection
and knowledge sharing. FTRs are commonly applied in large-scale or
safety-critical projects.

For this project FTRs were initially discussed since the group of five members.
The idea was that two members would implement the code, two would act as
reviewers, and the fifth might otherwise remain unaware of the changes unless
they investigated independently. A formal review meeting could have ensured
that all group members were consistently informed about new code and its
purpose. In principle, this would have created transparency and guaranteed
broader team involvement in defect detection.

However, given the small size and complexity of Phase 1 codebase, the overhead
of organising formal review meetings was judged disproportionate to the
potential benefits. Scheduling and conducting structured sessions would consume
time better spent on implementation and lightweight peer review. More
importantly, the group already achieves the central objectives of FTRs through
GitHub pull requests and peer review practices.
These tools already provide good review, defect detection, and visibility
across the team without requiring formal meetings. They also leave a
transparent history of comments, approvals, and revisions that can be revisited
at any time.

For these reasons, the group decided not to adopt the FTRs in Phase 1. The
current approach is lighter, faster, and more cost-efficient, while still
ensuring defects are caught, code standards are upheld and knowledge is shared.
Overall, FTRs were considered but rejected for this stage of the project.
