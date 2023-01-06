SELECT *FROM tbllibrary_books temp WHERE book_name LIKE '%中%';
DELETE FROM tblconsumption WHERE id LIKE '%213193904%';
UPDATE tblconsumption SET id=213191246 WHERE commodity_id="A12" AND commodity_name="康师傅";
SELECT course_id FROM tblcourse WHERE EXISTS (SELECT * FROM tblprivate_course WHERE tblcourse.course_id=tblprivate_course.course_id);
SELECT ID,NAME FROM A WHERE EXISTS (SELECT * FROM B WHERE A.ID=B.AID);