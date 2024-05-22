export const convertTimestampToDate = (timestamp) => {
  var dateResult = new Date(timestamp);
  return dateResult;
};

export const convertToDateFormat = (timestamp) => {
  var dateResult = convertTimestampToDate(timestamp);
  // Add leading zero for day and month if needed
  var day = ("0" + dateResult.getDate()).slice(-2);
  var month = ("0" + (dateResult.getMonth() + 1)).slice(-2);
  return day + "." + month + "." + dateResult.getFullYear();
};

export const convertToHours = (timestamp) => {
  var dateResult = convertTimestampToDate(timestamp);
  // Add leading zero for hours, minutes, and seconds if needed
  var hours = ("0" + dateResult.getHours()).slice(-2);
  var minutes = ("0" + dateResult.getMinutes()).slice(-2);
  var seconds = ("0" + dateResult.getSeconds()).slice(-2);
  return hours + ":" + minutes + ":" + seconds;
};

export const convertToDisplay = (timestamp) => {
  var dateResult = new Date(timestamp);
  var currentDate = new Date();

  if (dateResult < currentDate) return "Ended";

  var yearDiff = dateResult.getFullYear() - currentDate.getFullYear();
  var monthDiff = dateResult.getMonth() - currentDate.getMonth();
  var dayDiff = dateResult.getDate() - currentDate.getDate();
  var hourDiff = dateResult.getHours() - currentDate.getHours();
  var minuteDiff = dateResult.getMinutes() - currentDate.getMinutes();
  var secondDiff = dateResult.getSeconds() - currentDate.getSeconds();

  // Adjust for negative differences
  if (secondDiff < 0) {
    minuteDiff--;
    secondDiff += 60;
  }
  if (minuteDiff < 0) {
    hourDiff--;
    minuteDiff += 60;
  }
  if (hourDiff < 0) {
    dayDiff--;
    hourDiff += 24;
  }
  if (dayDiff < 0) {
    monthDiff--;
    var tempDate = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth(),
      0
    );
    dayDiff += tempDate.getDate();
  }
  if (monthDiff < 0) {
    yearDiff--;
    monthDiff += 12;
  }

  // Construct the result string
  var result = "";
  if (yearDiff > 0) {
    result += yearDiff + " Years";
    if (monthDiff > 0 || dayDiff > 0 || hourDiff > 0 || minuteDiff > 0)
      result += ", ";
  }
  if (monthDiff > 0) {
    result += monthDiff + " Months";
    if (dayDiff > 0 || hourDiff > 0 || minuteDiff > 0) result += ", ";
  }
  if (dayDiff > 0) {
    result += dayDiff + " Days";
    if (hourDiff > 0 || minuteDiff > 0) result += ", ";
  }
  if (hourDiff > 0) {
    result += hourDiff + " Hours";
    if (minuteDiff > 0) result += ", ";
  }
  if (minuteDiff > 0) {
    result += minuteDiff + " Minutes";
    if (hourDiff === 0 && secondDiff > 0) result += ", ";
  }
  if (secondDiff > 0 && hourDiff === 0) {
    result += secondDiff + " Seconds";
  }

  return result;
};
