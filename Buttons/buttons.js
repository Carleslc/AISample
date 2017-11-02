var infoAlert = $("#info");
var infoText = $("#info-text");
var learningRateField = $('#learningRate');
var desiredResultField = $("#desiredResult");
var inputsSection = $("#inputs");

var inputs = [0];
var weights = [0];
var learningRate = 0;
var realOutput = 0;
var trial = 1;
var errorLast = [-1, 0];
const ERROR_THRESHOLD = 0.005;

function evaluate() {
  var result = 0;
  inputs.forEach(function(inputValue, i) {
    layerValue = inputValue * weights[i];
    result += layerValue;
  });
  return result;
}

function learn(learningRate) {
  weights.forEach(function(weight, i) {
    if (inputs[i] > 0) {
      weights[i] = weight + learningRate;
    }
  });
  trial++;
}

function propagateError(error) {
  errorLast[0] = errorLast[1];
  errorLast[1] = error;
}

function isLoop(error) {
  return error == errorLast[0] || error == errorLast[1];
}

function isError(error) {
  return Math.abs(error) > ERROR_THRESHOLD;
}

function train() {
  clearAlert();
  println("<i>Training #" + trial + "</i>");
  output = evaluate();
  var error = info(realOutput, output);
  if (isError(error) && isLoop(error)) {
    learningRate = learningRate / 2;
  }
  propagateError(error);
  if (error > ERROR_THRESHOLD) {
    learn(learningRate);
  } else if (error < -ERROR_THRESHOLD) {
    learn(-learningRate);
  } else {
    println("Trained!");
  }
  return error;
}

function trainFull() {
  var error = train();
  while (isError(error)) {
    error = train();
  }
}

function reset() {
  weights = weights.fill(0);
  trial = 1;
  errorLast = [-1, 0];
  start();
  clearAlert();
}

function error(desired, actual) {
  return desired - actual;
}

function fix(value) {
  return value.toFixed(2);
}

function appendAlert(appendHtml) {
  infoAlert.show();
  infoText.html(infoText.html() + appendHtml.replace("\n", "<br/>")); 
}

function clearAlert() {
  infoAlert.hide();
  infoText.html("");
}

function println(message) {
  appendAlert(message + "\n");
}

function setOutputStream(stream) {
  infoAlert = $("#" + stream);
  infoText = $("#" + stream + "-text");
}

function arrayToString(array) {
  return "[" + array.map(fix).join(', ') + "]";
}

function info(realOutput, output) {
  println("<b>Neural Net output:</b> " + fix(output));
  println("<b>Weights:</b> " + arrayToString(weights));
  errorValue = error(realOutput, output);
  println("<b>Error:</b> " + fix(Math.abs(errorValue)));
  return errorValue;
}

function buttons() {
  return $(".form-check").length;
}

function addButton() {
  i = buttons() + 1;
  inputsSection.append(
      '<div class="form-check form-check-inline">\
        <label class="form-check-label">\
          <input class="form-check-input position-static" type="checkbox" id="input' + i + '" value="input' + i + '" aria-label="input ' + i + '">\
        </label>\
      </div>');
  inputs.push(0);
  weights.push(0);
}

function removeButton() {
  if (buttons() > 1) {
    inputsSection.contents().last().remove();
    inputs.pop();
    weights.pop();
  }
}

function initialLearningRate() {
  return parseFloat(learningRateField.val());
}

function desiredResult() {
  return parseFloat(desiredResultField.val());
}

function validate(field, validation) {
  if (!validation) {
    field.addClass("is-invalid");
    return false;
  } else {
    field.removeClass("is-invalid");
  }
  return true;
}

function valid() {
  var valid = true;
  var learningRate = initialLearningRate();
  if (!validate(learningRateField, !isNaN(learningRate) && learningRate > 0)) valid = false;
  if (!validate(desiredResultField, !isNaN(desiredResult()))) valid = false;
  return valid;
}

function start() {
  if (!valid()) return;
  $('#training').show();
  $('#inputsForm').addClass("margin-top");
  learningRate = initialLearningRate();
  realOutput = desiredResult();
  for (i = 0; i < inputs.length; i++) {
    inputs[i] = + $("#input" + (i + 1)).is(":checked");
  }
  setOutputStream("real-info");
  clearAlert();
  println("<b>Real output:</b> " + fix(realOutput));
  println("<b>Inputs:</b> " + arrayToString(inputs));
  setOutputStream("info");
}